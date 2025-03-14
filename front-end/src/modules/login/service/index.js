import axios from "axios";
import {login} from "../redux";
import {finalUrl} from "../../../env/Config";

const domain = finalUrl

export const loginUser = (data) => async (dispatch) => {
    try {
        const response = await axios.post(domain + '/identity/auth/login', data);
        dispatch(login(response.data));
        return response.data;
    } catch (error) {
        console.log(error);
    }
}