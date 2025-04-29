import axios from "../../../env/Config";
import { getAll} from "../redux";

export const getAllOrders = (params) => async (dispatch) => {
    try {
        const response = await axios.get( `/orders/getAll`,{
            params:params
        });
        dispatch(getAll(response.data));
    } catch (error) {
        console.log(error);
    }
};

export const addOrUpdateOrders = (orders) => async (dispatch) => {
    try {
        const response = await axios.post(`/orders/addOrUpdate`,orders);
        return response.data
    } catch (error) {
        console.log(error);
    }
};

export const autoGenOrders = (params) => async (dispatch) => {
    try {
        const response = await axios.get(`/orders/autoGenOrders`,{
            params:params
        });
        return response.data
    } catch (error) {
        console.log(error);
    }
};

export const activationOfTurnOff = (id) => async () => {
    try {
        const res = await axios.post(`/orders/changeStatus`, null,{
            params: {
                id : id
            }
        });
        return res.data
    } catch (error) {
        console.log(error);
    }
};