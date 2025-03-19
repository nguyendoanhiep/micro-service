import axios from "../../../env/Config";
import {getAll,getByCustomerId, getAllCustomerIds} from "../redux";

export const getAllVoucher = (params) => async (dispatch) => {
    try {
        const response = await axios.get( `/product/voucher/getAll`,{
            params: params
        });
        await dispatch(getAll(response.data));
    } catch (error) {
        console.log(error);
    }
};

export const getCustomerIdsById = (id) => async (dispatch) => {
    try {
        const response = await axios.get( `/product/voucher/getCustomerIdsById`,{
            params: {voucherId:id}
        });
        await dispatch(getAllCustomerIds(response.data.data));
    } catch (error) {
        console.log(error);
    }
};

export const addOrUpdateVoucher = (voucher) => async (dispatch) => {
    try {
        const response = await axios.post(`/product/voucher/addOrUpdate`,voucher);
        return response.data
    } catch (error) {
        console.log(error);
    }
};

export const getVouchersByCustomerId = (customerId) => async (dispatch) => {
    try {
        const response = await axios.get(`/product/voucher/getByCustomerId`,{
            params: {
                customerId: customerId,
            }
        });
        await dispatch(getByCustomerId(response.data));
    } catch (error) {
        console.log(error);
    }
};

export const deleteVoucher = (id) => async () => {
    try {
        const res = await axios.post(`/product/voucher/delete`, null,{
            params: {
                id : id
            }
        });
        return res.data
    } catch (error) {
        console.log(error);
    }
};

export const AssignCus = (data) => async () => {
    try {
        const res = await axios.post( `/product/voucher/AssignCus`,data);
        return res.data
    } catch (error) {
        console.log(error);
    }
};