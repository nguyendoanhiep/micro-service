import axios from "../../../env/Config";
import {getALl} from "../redux";

export const addOrUpdateRole = (role) => async (dispatch) => {
    try {
        const response = await axios.post('/identity/role/addOrUpdate', role);
        return response.data;
    } catch (error) {
        console.log(error);
    }
};

export const getAllRole = (params) => async (dispatch) => {
    try {
        const response = await axios.get( '/identity/role/getAll', {
            params: params
        });
        dispatch(getALl(response.data));
    } catch (error) {
        console.log(error);
    }
};


export const deleteRole = (id) => async (dispatch) => {
    try {
        const response = await axios.post( '/identity/role/delete?id='+ id);
        return response.data;
    } catch (error) {
        console.log(error);
    }
};

export const binding = (bindingRequest) => async (dispatch) => {
    try {
        const response = await axios.post('/identity/role/binding', bindingRequest);
        return response.data;
    } catch (error) {
        console.log(error);
    }
};