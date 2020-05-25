import { API_URL } from "./../../config";
import axios from "axios"

export default class LoginService 
{
    constructor(self) 
    {
        this.self = self;
    }


    static login(x)
    {
        return axios.post(API_URL + "/rest/users/login", x);
    }
    static info(x)
    {
        return axios.get(API_URL + "/rest/users/current");
    }

    static online() {
        return axios.get(API_URL + "/rest/users/loggedIn");
    }

    static logout() {
        return axios.delete(API_URL + "/rest/users/loggedIn");
    }
}
