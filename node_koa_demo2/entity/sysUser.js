const { Sequelize, DataTypes } = require('sequelize')
const moment = require('moment')
const sequelize = require('../config/sequelizeConfig')

const sysUser = sequelize.define('sysUser', {
  asid: {
    type: DataTypes.INTEGER,
    primaryKey: true,
    allowNull: false,
    unique: true,
    autoIncrement: true
  },
  id: {
    type: DataTypes.STRING
  },
  userName: {
    type: DataTypes.STRING,
    field: 'user_name'
  },
  password: {
    type: DataTypes.STRING
  },
  name: {
    type: DataTypes.STRING
  },
  idcardNo: {
    type: DataTypes.STRING,
    field: 'idcard_no'
  },
  sex: {
    type: DataTypes.INTEGER
  },
  orgCode: {
    type: DataTypes.STRING,
    field: 'org_code'
  },
  orgName: {
    type: DataTypes.STRING,
    field: 'org_name'
  },
  createTime: {
    type: DataTypes.DATE,
    field: 'create_time',
    // 获取当前时间
    defaultValue: Sequelize.NOW,
    // 时间格式化
    get() {
      return moment(this.getDataValue('create_time')).format('YYYY-MM-DD HH:mm:ss');
    }
  }
},{
  tableName: 'sys_user'
})

// 会创建表
// sysUser.sync({
//   force: false
// })

module.exports = sysUser
