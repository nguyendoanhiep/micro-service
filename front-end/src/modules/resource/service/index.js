import axios from "../../../env/Config";
import {getALl} from "../redux";

export const addOrUpdateResource = (role) => async (dispatch) => {
    try {
        const response = await axios.post('/identity/resource/addOrUpdate', role);
        return response.data;
    } catch (error) {
        console.log(error);
    }
};

export const getAllResource = () => async (dispatch) => {
    try {
        const response = await axios.get( '/identity/resource/getAll');
        dispatch(getALl(response.data));
    } catch (error) {
        console.log(error);
    }
};


export const deleteResource = (id) => async (dispatch) => {
    try {
        const response = await axios.post( '/identity/resource/delete?id='+ id);
        return response.data;
    } catch (error) {
        console.log(error);
    }
};