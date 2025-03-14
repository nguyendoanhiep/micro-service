import {baseUrl, finalUrl} from "../../../env/Config";
import axios from "axios";
import { getAll} from "../redux";

const domain = finalUrl
export const getAllOrders = (params) => async (dispatch) => {
    try {
        const response = await axios.get(domain + `/orders/getAll`,{
            params:params
        });
        dispatch(getAll(response.data));
    } catch (error) {
        console.log(error);
    }
};

export const addOrUpdateOrders = (orders) => async (dispatch) => {
    try {
        const response = await axios.post(domain + `/orders/addOrUpdate`,orders);
        return response.data
    } catch (error) {
        console.log(error);
    }
};

export const activationOfTurnOff = (id) => async () => {
    try {
        const res = await axios.post(domain + `/orders/changeStatus`, null,{
            params: {
                id : id
            }
        });
        return res.data
    } catch (error) {
        console.log(error);
    }
};