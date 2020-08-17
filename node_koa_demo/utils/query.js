const mysql = require('mysql')
const mysqlConfig = require('../config/mysqlConfig') // 数据库配置


// mysql
const pool = mysql.createPool(mysqlConfig)

// query sql语句入口
const query = (sql, val) => {
  return new Promise((resolve, reject) => {
    pool.getConnection(function (err, connection) {
      if (err) {
        reject(err)
      }
      else {
        connection.query(sql, val, (err, fields) => {
          if (err) {
            reject(err)
          }
          else {
            resolve(fields)
          }
          connection.release()
        })
      }
    })
  })
}

module.exports = {
  query
}
