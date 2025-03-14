import React, {useEffect, useState} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {Button, Form, Input, Modal, Pagination, Select, Table, Tree} from 'antd';
import {toast} from "react-toastify";
import {addOrUpdateRole, binding, deleteRole, getAllRole} from "../service";
import {getAllResource} from "../../resource/service";

const {Search} = Input;
const Role = () => {
    const columns = [
        {
            title: 'Name',
            dataIndex: 'name',
            key: 'name',
            width: 160
        },
        {
            title: 'Trạng thái',
            dataIndex: 'status',
            key: 'status',
            width: 120,
            render: (text) => {
                switch (text) {
                    case 1:
                        return <span className='status-active'>Đang hoạt động</span>
                    case 2:
                        return <span className='status-inactive'>Không hoạt động</span>
                    default:
                        return 'Không rõ';
                }
            },
        },
        {
            title: 'Action',
            dataIndex: '',
            key: 'x',
            fixed: 'right',
            align: 'center',
            render: (text, record) => (
                <span>
                    <Button style={{marginLeft: 5, width: 130 ,  backgroundColor: "#FFC125"}} type="primary"
                             onClick={() => openIsAddPermission(record)}>Add Permission</Button>
                 <Button style={{marginLeft: 5, width: 70}} type="primary"
                         onClick={() => openAddOrUpdate(record)}>Edit</Button>
                 <Button style={{marginLeft: 5, width: 70}} type="primary"
                         onClick={() => handleDelete(record.id)} danger>Delete</Button>
                </span>
            ),
            width: 140
        },
    ];

    const STATUS_OPTIONS = [
        {value: 1, label: 'Đang hoạt động'},
        {value: 2, label: 'Không hoạt động'},
    ];

    const dispatch = useDispatch();
    const [isAddOrUpdate, setIsAddOrUpdate] = useState(false);
    const [isAddPermission, setIsAddPermission] = useState(false);
    const [isCreate, setIsCreate] = useState(false);
    const [isLoading, setIsLoading] = useState(false)
    const [checkedKeys, setCheckedKeys] = useState([]);
    const [roleIds, setRoleIds] = useState();


    const [params, setParams] = useState({
        page: null,
        size: null,
        name: null,
        status: null
    });
    const roleList = useSelector((state) => state.role.roles);
    const resourceList = useSelector((state) => state.resource.resources);
    const [roleForm] = Form.useForm();
    const openAddOrUpdate = (record) => {
        setIsAddOrUpdate(true)
        if (record) {
            roleForm.setFieldsValue({
                id: record.id,
                name: record.name,
                status: record.status,
            });
            setIsCreate(false)
        } else {
            roleForm.setFieldsValue({
                status: 1
            });
            setIsCreate(true)
        }
    };

    const openIsAddPermission = (record) => {
        setIsAddPermission(true)
        dispatch(getAllResource())
        setCheckedKeys(record.resources.map(data => data.id.toString()))
        setRoleIds(record.id)
    };

    const handleBinding = async () => {
        const body = {roleId: roleIds , resourceIds : checkedKeys.checked }
        if(!body.resourceIds){
            return toast.warning('Chưa có thay đổi gì!', {
                className: 'my-toast',
                position: "top-center",
                autoClose: 2000,
            });
        }
        const res = await dispatch(binding(body))
        if (res.code === 200) {
            toast.success('Binding thành công!', {
                className: 'my-toast',
                position: "top-center",
                autoClose: 2000,
            });
            setIsAddPermission(false);
            setIsLoading(!isLoading)
        }else {
            toast.error('Binding thất bại! đã có lỗi xảy ra', {
                className: 'my-toast',
                position: "top-center",
                autoClose: 2000,
            });
        }
    }
    const onSearch = async (value) => {
        const newParams = {...params, name: value ? value : null}
        setParams(newParams)
        dispatch(getAllRole(newParams))
    };
    const handleAddOrUpdateRole = async () => {
        const res = await dispatch(addOrUpdateRole(roleForm.getFieldsValue()))
        if (res.code === 200) {
            toast.success(roleForm.getFieldsValue().id ? 'Cập nhập thành công' : 'Thêm mới thành công!', {
                className: 'my-toast',
                position: "top-center",
                autoClose: 2000,
            });
            setIsAddOrUpdate(false);
            setIsLoading(!isLoading)
            roleForm.resetFields()
        }else {
            toast.error('Thêm Role thất bại! đã có lỗi xảy ra', {
                className: 'my-toast',
                position: "top-center",
                autoClose: 2000,
            });
        }
    }
    const handleDelete = async (id) => {
        const res = await dispatch(deleteRole(id))
        if (res.code === 200) {
            toast.success('Xóa Role thành công!', {
                className: 'my-toast',
                position: "top-center",
                autoClose: 2000,
            });
            setIsLoading(!isLoading)
        }else {
            toast.error('Xóa Role thất bại! đã có lỗi xảy ra', {
                className: 'my-toast',
                position: "top-center",
                autoClose: 2000,
            });
        }
    };

    const handlePageChange = (e) => {
        const newParams = {...params, page: e}
        setParams(newParams)
        dispatch(getAllRole(newParams))

    }

    const convertResourcesToTreeItems = (resources) => {
        return resources.map(resource => ({
            key: resource.id.toString(),
            title: resource.name + " - " + resource.path ,
            children: resource.resourceChildren?.map(child => ({
                key: child.id.toString(),
                title: child.name + " - " + child.path,
            })) || []
        }));
    };

    const onCheck = (checkedKeysValue) => {
        setCheckedKeys(checkedKeysValue);
    };
    useEffect(() => {
        dispatch(getAllRole(params))
    }, [isLoading])

    return (
        <div style={{position: 'relative'}}>
            <div style={{
                display: 'flex',
                justifyContent: ' space-between'
            }}>
                <div>
                    <Select
                        placeholder="Select a status"
                        options={STATUS_OPTIONS}
                        onChange={(e) => setParams({...params, status: e})}
                    />
                    <Search
                        placeholder="Nhập tên Role"
                        allowClear
                        style={{
                            width: 250,
                            marginBottom: 20
                        }}
                        onSearch={value => onSearch(value)}
                    />
                </div>
                <div>
                    <Button onClick={() => openAddOrUpdate()}
                            type="primary"
                            style={{
                                backgroundColor: "#00CC00",
                                minHeight: 32
                            }}> Thêm Role</Button>
                </div>
            </div>
            <Table
                rowKey={record => record.id}
                columns={columns}
                dataSource={roleList.content}
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
                total={roleList.totalElements}
                onChange={handlePageChange}
                style={{
                    minWidth: 200,
                    float: "right",
                    margin: 15,
                    alignSelf: 'flex-end'
                }}/>
            <Modal title={isCreate ? "Thêm role mới" : "Chỉnh sửa thông tin"} open={isAddOrUpdate}
                   footer={null}
                   onCancel={() => {
                       setIsAddOrUpdate(false)
                       roleForm.resetFields()
                   }}>
                <Form
                    form={roleForm}
                    onFinish={handleAddOrUpdateRole}
                    name="role"
                    labelCol={{span: 8}}
                    wrapperCol={{span: 12}}>
                    <Form.Item
                        name="id"
                        hidden={true}/>
                    <Form.Item
                        label="Nhập tên Role : "
                        name="name"
                        rules={[
                            {required: true, message: 'Please input role name!'},
                            {min: 4, message: 'role name must have a minimum of 4 characters!'},
                        ]}>
                        <Input
                            style={{width: 300}}
                            type="text"
                        />
                    </Form.Item>
                    <Form.Item
                        label="Nhập trạng thái : "
                        name="status">
                        <Select
                            style={{width: 200}}
                            options={STATUS_OPTIONS}
                        />
                    </Form.Item>
                    <Form.Item
                        wrapperCol={{
                            offset: 15,
                            span: 16,
                        }}>
                        <Button
                            type="primary"
                            htmlType="submit"
                            style={{margin: 5}}>
                            Submit
                        </Button>
                        <Button htmlType="button"
                                style={{margin: 5}}
                                onClick={() => {
                                    setIsAddOrUpdate(false)
                                    roleForm.resetFields()
                                }}>
                            Cancel
                        </Button>
                    </Form.Item>
                </Form>
            </Modal>
            <Modal title={"Binding permission"}
                   open={isAddPermission}
                   footer={null}
                   onCancel={() => {
                       setIsAddPermission(false)
                       setCheckedKeys([])
                   }}>
                <Tree
                    checkable
                    multiple={true}
                    checkStrictly={true}
                    onCheck ={onCheck}
                    treeData={convertResourcesToTreeItems(resourceList)}
                    checkedKeys={checkedKeys}
                >
                </Tree>
                   <div style={{display:"flex",justifyContent:"right"}}>
                       <Button
                           type="primary"
                           style={{margin: 5}}
                           onClick={async () => {
                              await handleBinding()
                           }}>Submit
                       </Button>
                       <Button htmlType="button"
                               style={{margin: 5}}
                               onClick={() => {
                                   setIsAddPermission(false)
                                   setCheckedKeys([])
                               }}>
                           Cancel
                       </Button>
                   </div>
            </Modal>
        </div>

    )
}
export default Role;