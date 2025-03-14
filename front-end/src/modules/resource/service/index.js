import axios from 'axios';
import  {finalUrl} from '../../../env/Config'
import {getALl} from "../redux";

const domain = finalUrl
export const addOrUpdateResource = (role) => async (dispatch) => {
    try {
        const response = await axios.post(domain + '/identity/resource/addOrUpdate', role);
        return response.data;
    } catch (error) {
        console.log(error);
    }
};

export const getAllResource = () => async (dispatch) => {
    try {
        const response = await axios.get(domain + '/identity/resource/getAll');
        dispatch(getALl(response.data));
    } catch (error) {
        console.log(error);
    }
};


export const deleteResource = (id) => async (dispatch) => {
    try {
        const response = await axios.post(domain + '/identity/resource/delete?id='+ id);
        return response.data;
    } catch (error) {
        console.log(error);
    }
};