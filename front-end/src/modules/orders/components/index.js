import {Button, DatePicker, Input, Pagination, Select, Table} from "antd";
import {useDispatch, useSelector} from "react-redux";
import React, {useEffect, useState} from "react";
import {toast} from "react-toastify";
import {activationOfTurnOff, addOrUpdateOrders, autoGenOrders, getAllOrders} from "../service";
import {getAllProduct} from "../../product/service";
import axios, {baseUrl} from "../../../env/Config";
import dayjs from "dayjs";

const {Search} = Input;

const Orders = () => {
    const columns = [
        {
            title: 'Tên khách hàng',
            dataIndex: 'customerName',
            key: 'customerName',
            width: 160
        },
        {
            title: 'Số điện thoại',
            dataIndex: 'numberPhone',
            key: 'name',
            width: 140
        },
        {
            title: 'Mã đơn hàng',
            dataIndex: 'code',
            key: 'code',
            width: 160
        },
        {
            title: 'Voucher sử dụng',
            dataIndex: 'voucherName',
            key: 'voucherName',
            width: 180
        },
        {
            title: 'Giá gốc',
            dataIndex: 'originalTotalValue',
            key: 'originalTotalValue',
            width: 110
        },
        {
            title: 'Giảm giá',
            dataIndex: 'discountAmount',
            key: 'discountAmount',
            width: 110
        },
        {
            title: 'Tổng giá trị',
            dataIndex: 'totalValue',
            key: 'totalValue',
            width: 110
        },
        {
            title: 'Trạng thái',
            dataIndex: 'status',
            key: 'status',
            width: 130,
            render: (text) => {
                switch (text) {
                    case 1:
                        return <span className='status-active'>Đang hoạt động</span>
                    case 2:
                        return <span className='status-inactive'>Vô hiệu hóa</span>
                    default:
                        return 'Không rõ';
                }
            },
        },
        {
            title: 'Ngày tạo',
            dataIndex: 'createDate',
            key: 'createDate',
            width: 120,
        },
        {
            title: 'Action',
            dataIndex: '',
            key: 'x',
            fixed: 'right',
            render: (record) => (
                <span>
                 <Button
                     type="primary"
                     style={{marginLeft: 5, width: 110, backgroundColor: record.status === 1 ? '#FFA500' : '#00CC00'}}
                     onClick={() => handleActivationOfTurnOff(record)}> {record.status === 1 ? 'Vô hiệu hóa' : 'Kích hoạt lại'}</Button>
                </span>
            ),
            width: 140
        },
    ];

    const STATUS_OPTIONS = [
        {value: 1, label: 'Đang hoạt động'},
        {value: 2, label: 'Không hoạt động'},
        {value: 3, label: 'Đã sử dụng'},
    ];

    const dispatch = useDispatch();

    const ordersList = useSelector((state) => state.orders.orders);
    const [createDate , setCreateDate] = useState()
    const today = new Date();
    const oneWeekAgo = new Date();
    oneWeekAgo.setDate(today.getDate() - 7);

    const [fromDate, setFromDate] = useState(oneWeekAgo);
    const [toDate, setToDate] = useState(today);

    const [params, setParams] = useState({
        page: 1,
        size: 10,
        search: '',
        status: 0,
        fromDate:fromDate,
        toDate:toDate,
    });

    useEffect(() => {
        setParams(prevParams => ({
            ...prevParams,
            fromDate: fromDate,
            toDate: toDate,
        }));
        console.log(params)
    }, [fromDate, toDate]);
    const handleActivationOfTurnOff = async (record) => {
        const res = await dispatch(activationOfTurnOff(record.id))
        if (res.code === 200) {
            toast.success('Thay đổi trạng thái thành công!', {
                className: 'my-toast',
                position: "top-center",
                autoClose: 2000,
            });
            dispatch(getAllOrders(params))
        }else {
            toast.error('Không thể Thay đổi trạng thái , đã có lỗi xảy ra!', {
                className: 'my-toast',
                position: "top-center",
                autoClose: 2000,
            });
        }
    };
    const onSearch = async (value) => {
        const newParams = {...params, search: value}
        setParams(newParams)
        await dispatch(getAllOrders(newParams))
    };

    const onHandleAutoScaleOrders = async (totalRecord) => {
        await dispatch(autoGenOrders({totalRecord,createDate}));
    }


    const handlePageChange = (e) => {
        const newParams = {...params, page: e}
        setParams(newParams)
        dispatch(getAllOrders(newParams))

    }
    const exportExcel = async () => {
        try {
            const response = await axios.get("/orders/exportExcel", {
                responseType: "blob", // Quan trọng để nhận file nhị phân
                onDownloadProgress: (progressEvent) => {
                    console.log(progressEvent)
                    if (progressEvent.lengthComputable) {
                        const percent = Math.round((progressEvent.loaded / progressEvent.total) * 100);
                        console.log(`Đang tải: ${percent}%`); // Hiển thị tiến trình trong console
                    }
                }
            });

            const url = window.URL.createObjectURL(new Blob([response.data]));
            const link = document.createElement("a");
            link.href = url;

            // Tên file tùy chỉnh
            const currentTime = new Date().toISOString().slice(0, 19).replace(/[:T]/g, "-");
            link.setAttribute("download", `orders-${currentTime}.xlsx`);

            document.body.appendChild(link);
            link.click();
            link.remove();
        } catch (error) {
            toast.error('Lỗi khi export Excel:', {
                className: 'my-toast',
                position: "top-center",
                autoClose: 2000,
            });
            console.error("Lỗi khi export Excel:", error);
        }
    }

    useEffect(() => {
        dispatch(getAllOrders(params))
        dispatch(getAllProduct({
            page: 1,
            size: 9999,
            name: null,
            status: null,
        }))
    }, [])
    return (
        <div style={{position: 'relative'}}>
            <div style={{
                display: 'flex',
                justifyContent: ' space-between'
            }}>
                <div>
                    <DatePicker placeholder={"From Date"}
                                defaultValue={dayjs(fromDate)}
                                onChange={(value)=>{
                                    setFromDate(value.toDate())
                                    console.log(value.toDate())
                                }} />
                    <DatePicker placeholder={"To Date"}
                                defaultValue={dayjs(toDate)}
                                onChange={(value)=>{
                                    setToDate(value.toDate())
                                }} />
                    <Select
                        placeholder="Select a status"
                        options={STATUS_OPTIONS}
                        onChange={(e) => setParams({...params, status: e})}
                    />
                    <Search
                        placeholder="Nhập mã đơn hàng "
                        allowClear
                        style={{
                            width: 250,
                            marginBottom: 20
                        }}
                        onSearch={value => onSearch(value)}
                    />
                </div>
                <div>
                    <Button style={{margin: 5, width: 120}} type="primary"
                            onClick={()=>exportExcel()}>Export Excel</Button>
                </div>
            </div>
            <div style={{
                display: 'flex',
                justifyContent: ' space-between'
            }}>
                <div>
                    <DatePicker onChange={(date)=>{ setCreateDate(date.toDate());}} />
                    <Search
                        placeholder="Nhập số đơn hàng muốn tạo tự động "
                        allowClear
                        style={{
                            width: 310,
                            marginBottom: 20
                        }}
                        onSearch={value => onHandleAutoScaleOrders(value)}
                    />
                </div>
            </div>
            <Table
                rowKey={record => record.id}
                columns={columns}
                dataSource={ordersList.content}
                pagination={false}
                bordered
                style={{
                    minHeight: 600
                }}
                scroll={{
                    x: 1100
                }}
            />
            <Pagination
                current={params.page}
                pageSize={params.size}
                total={ordersList.totalElements}
                onChange={handlePageChange}
                style={{
                    minWidth: 200,
                    float: "right",
                    margin: 15,
                    alignSelf: 'flex-end'
                }}/>
        </div>

    )
}

export default Orders