import { API_URL } from "./../../../config";
import axios from "axios"

export default class RunningAgentService 
{
    constructor(self) 
    {
        this.self = self;
    }

    static list() 
    {
        return axios.get(API_URL + "/agents/running/");
    }

    static delete(id) 
    {
        return axios.delete(API_URL + "/agents/running/" + id);
    }
}