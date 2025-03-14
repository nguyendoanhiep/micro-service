import {baseUrl, finalUrl} from "../../../env/Config";
import axios from "axios";
import {getAll,getByCustomerId, getAllCustomerIds} from "../redux";


const domain = finalUrl
export const getAllVoucher = (params) => async (dispatch) => {
    try {
        const response = await axios.get(domain + `/product/voucher/getAll`,{
            params: params
        });
        await dispatch(getAll(response.data));
    } catch (error) {
        console.log(error);
    }
};

export const getCustomerIdsById = (id) => async (dispatch) => {
    try {
        const response = await axios.get(domain + `/product/voucher/getCustomerIdsById`,{
            params: {voucherId:id}
        });
        await dispatch(getAllCustomerIds(response.data.data));
    } catch (error) {
        console.log(error);
    }
};

export const addOrUpdateVoucher = (voucher) => async (dispatch) => {
    try {
        const response = await axios.post(domain + `/product/voucher/addOrUpdate`,voucher);
        return response.data
    } catch (error) {
        console.log(error);
    }
};

export const getVouchersByCustomerId = (customerId) => async (dispatch) => {
    try {
        const response = await axios.get(domain + `/product/voucher/getByCustomerId`,{
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
        const res = await axios.post(domain + `/product/voucher/delete`, null,{
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
        const res = await axios.post(domain + `/product/voucher/AssignCus`,data);
        return res.data
    } catch (error) {
        console.log(error);
    }
};