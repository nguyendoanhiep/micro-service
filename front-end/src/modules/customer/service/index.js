import axios from "../../../env/Config";
import { findById, getAll} from "../redux";
export const getAllCustomer = (params) => async (dispatch) => {
    try {
        const response = await axios.get(`/identity/customer/getAll`,{
            params: params
        });
        dispatch(getAll(response.data));
    } catch (error) {
        console.log(error);
    }
};

export const findCustomerById = (id) => async (dispatch) => {
    try {
        const response = await axios.get( `/identity/customer/findById`,{
            params: {
                id:id
            }
        });
        dispatch(findById(response.data));
    } catch (error) {
        console.log(error);
    }
};

export const addOrUpdateCustomer = (customer) => async (dispatch) => {
    try {
        const response = await axios.post( `/identity/customer/addOrUpdate`,customer);
        return response.data
    } catch (error) {
        console.log(error);
    }
};
