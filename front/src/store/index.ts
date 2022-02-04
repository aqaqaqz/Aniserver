import { createStore } from "vuex";
import axios from 'axios';

//https://developerjournal.tistory.com/15 로그인 뷰엑스
export default createStore({
  state: {
    user : {
      id : '',
      login : false
    }
  },
  mutations: {
    checkLogin(state){
      axios.post('/api/user/checkLogin').then(rst => {
          if(rst.data.rstCode == "200"){
          state.user.id = rst.data.info.id;
          state.user.login = true;
        }
      })
    }
  },
  actions: {},
  modules: {},
});
