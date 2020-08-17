const mysqlModel = require('../utils/sysUser') //引入数据库方法

const findSysUserById = async (ctx, next) => {
  console.log(ctx.request.body)
  const { body } = ctx.request
  const user = await mysqlModel.findSysUserById(body.id)
  console.log(user)
  ctx.body = {
    code: 1,
    message: '查询成功',
    result: user
  }
}

module.exports = {
  findSysUserById
}
