const Sequelize = require('sequelize')
const { databaseConfig } = require('./baseConfig')

console.log('init sequelize...')

const sequelize = new Sequelize(
  databaseConfig.databaseName,
  databaseConfig.username,
  databaseConfig.password,
  {
    host: databaseConfig.host, //数据库地址
    dialect: 'mysql', //指定连接的数据库类型
    pool: {
      max: 5, //连接池最大连接数量
      min: 0, //最小连接数量
      idle: 10000, //如果一个线程 10秒内么有被使用过的话，就释放
    },
    define: {
      // 是否冻结表名
      // 默认情况下，表名会转换为复数形式
      freezeTableName: false,
      // 是否为表添加 createdAt 和 updatedAt 字段
      // createdAt 记录表的创建时间
      // updatedAt 记录字段更新时间
      timestamps: false,
      // 是否为表添加 deletedAt 字段
      // 默认情况下, destroy() 方法会删除数据，
      // 设置 paranoid 为 true 时，将会更新 deletedAt 字段，并不会真实删除数据。
      paranoid: false
    }
})

module.exports = sequelize
