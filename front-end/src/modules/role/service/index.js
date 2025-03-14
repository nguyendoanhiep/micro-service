import axios from 'axios';
import  {finalUrl} from '../../../env/Config'
import {getALl} from "../redux";

const domain = finalUrl
export const addOrUpdateRole = (role) => async (dispatch) => {
    try {
        const response = await axios.post(domain + '/identity/role/addOrUpdate', role);
        return response.data;
    } catch (error) {
        console.log(error);
    }
};

export const getAllRole = (params) => async (dispatch) => {
    try {
        const response = await axios.get(domain + '/identity/role/getAll', {
            params: params
        });
        dispatch(getALl(response.data));
    } catch (error) {
        console.log(error);
    }
};


export const deleteRole = (id) => async (dispatch) => {
    try {
        const response = await axios.post(domain + '/identity/role/delete?id='+ id);
        return response.data;
    } catch (error) {
        console.log(error);
    }
};

export const binding = (bindingRequest) => async (dispatch) => {
    try {
        const response = await axios.post(domain + '/identity/role/binding', bindingRequest);
        return response.data;
    } catch (error) {
        console.log(error);
    }
};