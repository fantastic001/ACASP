import { API_URL } from "./../../config";
import axios from "axios"

export default class UserService 
{
    constructor(self) 
    {
        this.self = self;
    }

    static list() 
    {
        return axios.get(API_URL + "/rest/users/registered");
    }

    static get(id) 
    {
        return axios.get(API_URL + "/rest/user/registered/" + id);
    }

    static create(x)
    {
        return axios.post(API_URL + "/rest/users/register", x);
    }
    static logOut() 
    {
      
    }
    static online() {
        return axios.get(API_URL + "/rest/users/loggedIn");
    }
}
