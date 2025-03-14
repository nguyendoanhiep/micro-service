import {createSlice} from '@reduxjs/toolkit';

const resourceSlice = createSlice({
    name: 'resource',
    initialState: { resources: []},
    reducers: {
        getALl: (state, action) => {
            state.resources = action.payload.data;
        },
    },
});
export const {getALl} = resourceSlice.actions;

export default resourceSlice.reducer;
