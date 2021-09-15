[quartz] spring boot quartz 설정
=======
quartz
-----
- quartz init
~~~
//build.gradle
implementation group: 'org.quartz-scheduler', name: 'quartz', version: '2.3.1' 
~~~

- 클러스터가 필요하지 않다고 결론이 나서 db에 배치에 필요한 정보를 따로 보관하고 서버가 올라가면서 해당 배치를 올리는 방법으로 개발.

- db에서 class혹은 프로시저 이름을 받아서 실행하다보니 di가 일어나지 않음.(new로 생성되는 경우) 
    - quartz 등록 시에 JobDataMap에 applicationContext를 넣어줘서 해결
~~~
//BaseJob.java

public class BaseJob extends QuartzJobBean{
    private ApplicationContext ctx;
	public void setCtx(ApplicationContext ctx) { this.ctx = ctx; }

    @Override
	protected void executeInternal(JobExecutionContext context) {
		try {
			ctx.getBean(BatchService.class).addBatchLog(batchId, "배치[" + batchId + "]가 실행되었습니다.");
			run();
		} catch(IllegalStateException e) {
			//서버 죽었는데 쿼츠 돌아가는 경우
			HamonicaBatch.shutdownSchedule();
		} catch(Exception e) {
			//배치 실행도중 에러
			try {
				ctx.getBean(BatchService.class).addBatchLog(batchId, "배치[" + batchId + "]가 오류로 완료되지 못했습니다.");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void run() throws JobExecutionException, IllegalStateException {
		//BaseJob extends 후 override
	}

    public Object getBean(Class clz){
		return ctx.getBean(clz);
	}
}
~~~

~~~
//Job.java

public class ProcedureJob extends BaseJob {
	
	private String procedureName;
	private String parameter;

	@Override
	public void run() {
		BatchService batchService = (BatchService)getBean(BatchService.class);
		
		try {
			batchService.excuteProcedureBatch(procedureName, parameter);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
~~~

- 프로시저 타입의 배치는 @SelectProvider 어노테이션을 이용하여 구현
    - type -> 실행시킬 method가 들어있는 class
    - method -> 실행시킬 method 명
    - 해당 method에는 받은 값들이 map형태로 넘어온다.
    - parameter를 string으로 받아서 query string을 만들어주고 리턴하면 마이바티스에서 알아서 해결해준다.
    - 프로시저에서 넣어준 rst_msg와 rst_code가 정상적으로 노출되는지 확인해본다.
~~~
//BatchInfoDao.java

@SelectProvider(type = BatchInfo.class, method = "excuteProcedureBatch")
Map<String, Object> excuteProcedureBatch(@Param("procedureName") String procedureName, @Param("parameter") String parameter) throws Exception;
~~~
~~~
public String excuteProcedureBatch(Map<String, Object> params) {
    String procedureName = (String)params.get("procedureName");
    String parameter = (String)params.get("parameter");
    parameter = parameter.substring(1);
    
    StringBuffer query = new StringBuffer();
    query.append("CALL "+procedureName+" (");
    
    JSONParser parser = new JSONParser();
    try {
        JSONObject json = (JSONObject)parser.parse(parameter);
        for(String key : (Set<String>)json.keySet()) {
            query.append(key + ":='" + json.get(key) + "', ");
        }
    } catch (ParseException e) {
        e.printStackTrace();
    }
    
    query.append("rst_msg := 'rst_msg', rst_code := 'rst_code'");
    query.append(")");
    
    
    return query.toString();
}
~~~
~~~
//BatchService.java

public void excuteProcedureBatch(String procedureName, String parameter) throws Exception {
    Map<String, Object> rst = batchInfoDao.excuteProcedureBatch(procedureName, parameter);
		
    System.out.println("--------------------->" + rst.get("rst_msg"));
    System.out.println("--------------------->" + rst.get("rst_code"));
}
~~~

