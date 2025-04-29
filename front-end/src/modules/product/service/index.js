import axios from "../../../env/Config";
import {getAll} from "../redux";
export const getAllProduct = (params) => async (dispatch) => {
    try {
        const response = await axios.get(`/product/getAll`, {
            params: params

        });
        dispatch(getAll(response.data));
    } catch (error) {
        console.log(error);
    }
};

export const addOrUpdateProduct = (product) => async (dispatch) => {
    try {
        const response = await axios.post(`/product/addOrUpdate`, product);
        return response.data

    } catch (error) {
        console.log(error);
    }
};


export const deleteProduct = (id) => async () => {
    try {
        const res = await axios.post( `/product/delete`, null,{
            params: {
                id : id
            }
        });
        return res.data
    } catch (error) {
        console.log(error);
    }
};