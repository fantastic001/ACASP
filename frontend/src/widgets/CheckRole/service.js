import { API_URL } from "./../../config";
import axios from "axios"

export default class CheckRoleService 
{
    constructor(self) 
    {
        this.self = self;
    }

    static get() 
    {
        return axios.get(API_URL + "/Auth/");
    }
}
