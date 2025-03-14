import axios from 'axios';
import  {finalUrl} from '../../../env/Config'
import {getALl} from "../redux";

const domain = finalUrl
export const registerUser = (user) => async (dispatch) => {
    try {
        const response = await axios.post(domain + '/identity/auth/register', user);
        return response.data;
    } catch (error) {
        console.log(error);
    }
};

export const getAllUser = (params) => async (dispatch) => {
    try {
        const response = await axios.get(domain + '/identity/user/getAll', {
            params: params
        });
        dispatch(getALl(response.data));
    } catch (error) {
        console.log(error);
    }
};

export const addOrUpdate = (user) => async (dispatch) => {
    try {
        const response = await axios.post(domain + '/identity/user/addOrUpdate', user);
        return response.data;
    } catch (error) {
        console.log(error);
    }
};

export const deleteUser = (id) => async (dispatch) => {
    try {
        const response = await axios.post(domain + '/identity/user/delete?id='+ id);
        return response.data;
    } catch (error) {
        console.log(error);
    }
};