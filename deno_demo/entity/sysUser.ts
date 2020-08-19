import { Model, DataTypes } from "../deps.ts";

class SysUserModel extends Model {
    static table = "sys_user";
    static fields = {
        asid: {
            primaryKey: true,
            autoIncrement: true,
            type: DataTypes.INTEGER
        },
        id: {
            type: DataTypes.STRING
        },
        userName: {
            type: DataTypes.STRING,
            field: "user_name"
        },
        password: {
            type: DataTypes.STRING
        },
        name: {
            type: DataTypes.STRING
        },
        idcardNo: {
            type: DataTypes.STRING,
            field: "idcard_no"
        },
        sex: {
            type: DataTypes.STRING
        },
        orgCode: {
            type: DataTypes.STRING,
            field: "org_code"
        },
        orgName: {
            type: DataTypes.STRING,
            field: "org_name"
        }
    };
}

export default SysUserModel;
