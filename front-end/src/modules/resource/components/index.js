import React, {useEffect, useState} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {Button, Form, Input, Menu, Modal, Pagination, Select, Table} from 'antd';
import {toast} from "react-toastify";
import {addOrUpdateResource, deleteResource, getAllResource} from "../service";
const {Search} = Input;
const Resource = () => {
   

    const METHOD_OPTIONS = [
        {value: 'GET', label: 'GET'},
        {value: "POST", label: 'POST'},
    ];

    const dispatch = useDispatch();
    const [isAddOrUpdate, setIsAddOrUpdate] = useState(false);
    const [isCreate, setIsCreate] = useState(false);
    const [isLoading, setIsLoading] = useState(false)

    const resourceList = useSelector((state) => state.resource.resources);
    const [resourceForm] = Form.useForm();
    const openAddOrUpdate = (record) => {
        setIsAddOrUpdate(true)
        if (record) {
            resourceForm.setFieldsValue(record);
            setIsCreate(false)
        } else {
            setIsCreate(true)
        }
    };
    const handleAddOrUpdateRole = async () => {
        const res = await dispatch(addOrUpdateResource(resourceForm.getFieldsValue()))
        if (res.code === 200) {
            toast.success(resourceForm.getFieldsValue().id ? 'Cập nhập thành công' : 'Thêm mới thành công!', {
                className: 'my-toast',
                position: "top-center",
                autoClose: 2000,
            });
            setIsAddOrUpdate(false);
            setIsLoading(!isLoading)
            resourceForm.resetFields()
        }else {
            toast.error(resourceForm.getFieldsValue().id ? 'Cập nhập ' : 'Thêm mới' + ' thất bại! đã có lỗi xảy ra', {
                className: 'my-toast',
                position: "top-center",
                autoClose: 2000,
            });
        }
    }
    const handleDelete = async (id) => {
        const res = await dispatch(deleteResource(id))
        if (res.code === 200) {
            toast.success('Xóa thành công!', {
                className: 'my-toast',
                position: "top-center",
                autoClose: 2000,
            });
            setIsLoading(!isLoading)
        }else {
            toast.error('Xóa thất bại! đã có lỗi xảy ra', {
                className: 'my-toast',
                position: "top-center",
                autoClose: 2000,
            });
        }
    };

    useEffect(() => {
        dispatch(getAllResource())
    }, [isLoading])
    const convertResourcesToMenuItems = (resources) => {
        return resources.map(resource => ({
            key: resource.id.toString(),
            label: (
                <div style={{
                    display: "grid",
                    gridTemplateColumns: "auto 1fr auto",
                    alignItems: "center",
                    padding: "10px 15px",
                    borderBottom: "1px solid #eee",
                }}>
                    <strong style={{width:400}}>{resource.name}</strong>
                    <span style={{ fontSize: "14px", color: "#666" }}>{resource.path}</span>
                    <div style={{ display: "flex", gap: "10px" }}>
                        <Button type="primary" onClick={() => openAddOrUpdate(resource)}>Edit</Button>
                        <Button type="primary" danger onClick={() => handleDelete(resource.id)}>Delete</Button>
                    </div>
                </div>
            ),
            children: resource.resourceChildren?.map(child => ({
                key: child.id.toString(),
                label: (
                    <div style={{
                        display: "grid",
                        gridTemplateColumns: "auto 1fr auto",
                        alignItems: "center",
                        padding: "10px 15px",
                        borderBottom: "1px solid #eee"
                    }}>
                        <strong style={{width:400}}>{child.name}</strong>
                        <span style={{ fontSize: "14px", color: "#666" }}>{child.path}</span>
                        <div style={{ display: "flex", gap: "10px" }}>
                            <Button type="primary" onClick={() => openAddOrUpdate(child)}>Edit</Button>
                            <Button type="primary" danger onClick={() => handleDelete(child.id)}>Delete</Button>
                        </div>
                    </div>
                ),
            })) || []
        }));
    };



    return (
        <div style={{position: 'relative'}}>
            <div style={{
                display: 'flex',
                justifyContent: 'right'
            }}>
                <Button onClick={() => openAddOrUpdate()}
                            type="primary"
                            style={{
                                backgroundColor: "#00CC00",
                                minHeight: 32,
                                marginBottom:10
                }}> Thêm Resource</Button>
            </div>
            <div>
                <Menu
                    mode="inline"
                    items={convertResourcesToMenuItems(resourceList)}
                />
            </div>
            <Modal title={isCreate ? "Thêm resource mới" : "Chỉnh sửa thông tin"} open={isAddOrUpdate}
                   footer={null}
                   onCancel={() => {
                       setIsAddOrUpdate(false)
                       resourceForm.resetFields()
                   }}>
                <Form
                    form={resourceForm}
                    onFinish={handleAddOrUpdateRole}
                    name="fole"
                    labelCol={{span: 8}}
                    wrapperCol={{span: 12}}>
                    <Form.Item
                        name="id"
                        hidden={true}/>
                    <Form.Item
                        label="Nhập tên : "
                        name="name"
                        rules={[
                            {required: true, message: 'Please input resource name!'},
                            {min: 4, message: 'name must have a minimum of 4 characters!'},
                        ]}>
                        <Input
                            style={{width: 300}}
                            type="text"
                        />
                    </Form.Item>
                    <Form.Item
                        label="Nhập path : "
                        name="path"
                        rules={[
                            {required: true, message: 'Please input resource name!'},
                            {min: 4, message: 'username must have a minimum of 4 characters!'},
                        ]}>
                        <Input
                            style={{width: 300}}
                            type="text"
                        />
                    </Form.Item>
                    <Form.Item
                        label="Nhập method : "
                        name="method">
                        <Select
                            style={{width: 200}}
                            options={METHOD_OPTIONS}
                        />
                    </Form.Item>
                    <Form.Item
                        label="Nhập root : "
                        name="parentId">
                        <Select
                            style={{width: 200}}
                            options={resourceList.map(resource => ({
                                label: resource.name,
                                value: resource.id
                            }))}
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
                                    resourceForm.resetFields()
                                }}>
                            Cancel
                        </Button>
                    </Form.Item>
                </Form>
            </Modal>
        </div>

    )
}
export default Resource;