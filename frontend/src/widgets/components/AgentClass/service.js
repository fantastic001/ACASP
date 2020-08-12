import { API_URL } from "./../../../config";
import axios from "axios"

export default class AgentClassService 
{
    constructor(self) 
    {
        this.self = self;
    }

    static list() 
    {
        return axios.get(API_URL + "/agents/classes/");
    }

    static run(fullname, name) {
        return axios.put(API_URL +  "/agents/running/" + fullname + "/" + name);
    }
}