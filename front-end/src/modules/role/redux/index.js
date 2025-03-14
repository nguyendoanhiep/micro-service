import {createSlice} from '@reduxjs/toolkit';

const roleSlice = createSlice({
    name: 'role',
    initialState: { roles: {}},
    reducers: {
        getALl: (state, action) => {
            state.roles = action.payload.data;
        },
    },
});
export const {getALl} = roleSlice.actions;

export default roleSlice.reducer;
