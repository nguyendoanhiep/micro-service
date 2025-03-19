import {login} from "../redux";
import axios from "../../../env/Config";


export const loginUser = (data) => async (dispatch) => {
    try {
        const response = await axios.post('/identity/auth/login', data);
        dispatch(login(response.data));
        return response.data;
    } catch (error) {
        console.log(error);
    }
}