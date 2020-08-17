const { Op } = require('sequelize')
const jwt = require('jsonwebtoken')
const sysUserModel = require('../entity/sysUser')
const { jwtConfig } = require('../config/baseConfig')
const resultInfo = require('../entity/resultInfo')

/**
 * 登录
 * @param ctx
 * @param next
 * @returns {Promise<void>}
 */
const login = async (ctx, next) => {
  console.log(ctx.request.body)
  try {
    const { body } = ctx.request
    let result = await sysUserModel.findOne({
      where: {
        user_name: body.username,
      }
    })
    if (result) {
      if (await result.password === body.password) {
        const token = jwt.sign({ name: body.username }, jwtConfig.secret, {expiresIn: jwtConfig.expiresIn})
        const data = {
          token: token
        }
        ctx.body = resultInfo.success('登录成功', data)
      } else {
        ctx.body = resultInfo.failure('登录失败，密码不正确')
      }
    } else {
      ctx.body = resultInfo.failure('登录失败，用户不存在')
    }
  } catch (error) {
    ctx.body = resultInfo.failure(error.toString())
  }

}

/**
 * 根据id获取单个用户信息
 * @param ctx
 * @param next
 * @returns {Promise<void>}
 */
const getById = async (ctx, next) => {
  console.log(ctx.request.body)
  try {
    const { body } = ctx.request
    let result = await sysUserModel.findOne({
      where: {
        id: body.id,
      }
    })
    ctx.body = resultInfo.success('查询成功', result)
  } catch (error) {
    ctx.body = resultInfo.failure(error.toString())
  }

}

/**
 * 获取用户信息列表
 * @param ctx
 * @param next
 * @returns {Promise<void>}
 */
const getSysUserList = async (ctx, next) => {
  console.log(ctx.request.body)
  try {
    console.info(ctx.headers['user-agent'])
    const token = ctx.header.authorization
    console.info(token)
    const tokenInfo = jwt.verify(token.split(' ')[1], jwtConfig.secret)
    console.info(tokenInfo.name)
    const { body } = ctx.request
    const limit = body.limit
    const page = (body.page - 1) * limit
    let criteria = {}
    if (body.name) {
      criteria['name'] = body.name
    }
    if (body.userName) {
      criteria['user_name'] = {[Op.like]: body.userName+'%'}
    }
    const result = await sysUserModel.findAndCountAll({
      where: criteria,
      order: [['asid', 'DESC']],
      offset: page,
      limit: limit
    })
    console.log(JSON.stringify(result))
    ctx.body = resultInfo.success('查询成功', result.rows, result.count)
  } catch (error) {
    ctx.body = resultInfo.failure(error.toString())
  }
}

/**
 * 添加用户信息
 * @param ctx
 * @param next
 * @returns {Promise<void>}
 */
const addSysUser = async (ctx, next) => {
  console.log(ctx.request.body)
  try {
    const { body } = ctx.request
    const result = await sysUserModel.create(body)
    ctx.body = resultInfo.success('添加成功')
  } catch (error) {
    ctx.body = resultInfo.failure(error.toString())
  }

}

/**
 * 更新用户信息
 * @param ctx
 * @param next
 * @returns {Promise<void>}
 */
const updateSysUser = async (ctx, next) => {
  console.log(ctx.request.body)
  try {
    const { body } = ctx.request
    const result = await sysUserModel.update(body, {
      where: {
        id: body.id,
      }
    })
    ctx.body = resultInfo.success('更新成功')
  } catch (error) {
    ctx.body = resultInfo.failure(error.toString())
  }

}

/**
 * 删除用户信息
 * @param ctx
 * @param next
 * @returns {Promise<void>}
 */
const deleteSysUser = async (ctx, next) => {
  console.log(ctx.request.body)
  try {
    const { body } = ctx.request
    await sysUserModel.destroy({
      where: {
        id: body.id,
      }
    })
    ctx.body = resultInfo.success('删除成功')
  } catch (error) {
    ctx.body = resultInfo.failure(error.toString())
  }
}

module.exports = {
  login,
  getById,
  getSysUserList,
  addSysUser,
  updateSysUser,
  deleteSysUser
}


