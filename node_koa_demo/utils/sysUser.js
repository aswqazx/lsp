const {query} = require('./query.js')

const findSysUserById = async function(id) {
  const _sql = `SELECT * FROM sys_user where id="${id}" limit 1;`
  const result = await query(_sql)
  return result
}

module.exports = {
  findSysUserById
}
