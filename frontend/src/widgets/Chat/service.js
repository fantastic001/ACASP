import { API_URL } from "./../../config";
import axios from "axios"

export default class ChatService 
{
    constructor(self) 
    {
        this.self = self;
    }

    static list() 
    {
        return axios.get(API_URL + "/rest/messages/");
    }

    static send(to, subject, text) 
    {
        return axios.post(API_URL + "/rest/messages/user/", {
            fromUsername: localStorage.getItem("user_id"),
            toUsername: to,
            subject: subject,
            text: text
        });
    }
    static broadcast(subject, text) 
    {
        return axios.post(API_URL + "/rest/messages/all", {
            fromUsername: localStorage.getItem("user_id"),
            toUsername: "",
            subject: subject,
            text: text
        });
    }

}
