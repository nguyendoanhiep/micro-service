import axios from "../../../env/Config";
import {getALl} from "../redux";


export const registerUser = (user) => async (dispatch) => {
    try {
        const response = await axios.post( '/identity/auth/register', user);
        return response.data;
    } catch (error) {
        console.log(error);
    }
};

export const getAllUser = (params) => async (dispatch) => {
    try {
        const response = await axios.get('/identity/user/getAll', {
            params: params
        });
        dispatch(getALl(response.data));
    } catch (error) {
        console.log(error);
    }
};

export const addOrUpdate = (user) => async (dispatch) => {
    try {
        const response = await axios.post( '/identity/user/addOrUpdate', user);
        return response.data;
    } catch (error) {
        console.log(error);
    }
};

export const deleteUser = (id) => async (dispatch) => {
    try {
        const response = await axios.post('/identity/user/delete?id='+ id);
        return response.data;
    } catch (error) {
        console.log(error);
    }
};