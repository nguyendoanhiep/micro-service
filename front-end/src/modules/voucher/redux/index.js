import {createSlice} from "@reduxjs/toolkit";

const voucherSlice = createSlice({
    name: 'voucher',
    initialState: { vouchers: {}  , vouchersByCustomerId : [] , customerIds : []},
    reducers: {
        getAll: (state, action) => {
            state.vouchers = action.payload.data;
        },
        addOrUpdate : (state,action) => {
        },
        getByCustomerId : (state,action) => {
            state.vouchersByCustomerId = action.payload.data
        },
        getAllCustomerIds : (state,action) => {
            state.customerIds = action.payload
        }
    },
});

export const {getAll,getByCustomerId,getAllCustomerIds} = voucherSlice.actions;

export default voucherSlice.reducer;