import { constantRoutes } from '@/router'
import Layout from '@/layout'
import { getRoutes } from '@/api/role'

/**
 * Use meta.role to determine if the current user has permission
 * @param roles
 * @param route
 */
function hasPermission(roles, route) {
  if (route.meta && route.meta.roles) {
    return roles.some(role => route.meta.roles.includes(role))
  } else {
    return true
  }
}

/**
 * Filter asynchronous routing tables by recursion
 * @param routes asyncRoutes
 * @param roles
 */
export function filterAsyncRoutes(routes, roles) {
  const res = []

  routes.forEach(route => {
    const tmp = { ...route }
    if (hasPermission(roles, tmp)) {
      if (tmp.children) {
        tmp.children = filterAsyncRoutes(tmp.children, roles)
      }
      res.push(tmp)
    }
  })

  return res
}

export function filterAsyncServerRolesRouter(routes) {
  const res = []
  routes.forEach(item => {
    const tmp = { ...item }
    if (tmp.component === 'Layout') {
      tmp.component = Layout
    } else {
      const component = tmp.component
      tmp.component = (resolve) => require([`@/views/${component}.vue`], resolve)
    }
    if (tmp.children) {
      tmp.children = filterAsyncServerRolesRouter(tmp.children)
    }
    res.push(tmp)
  })
  return res
}

const state = {
  routes: [],
  addRoutes: []
}

const mutations = {
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes
    state.routes = constantRoutes.concat(routes)
  }
}

const actions = {
  generateRoutes({ commit }, roles) {
    return new Promise(resolve => {
      let accessedRoutes
      /* if (roles.includes('admin')) {
        accessedRoutes = asyncRoutes || []
      } else {
        accessedRoutes = filterAsyncRoutes(asyncRoutes, roles)
      }
      commit('SET_ROUTES', accessedRoutes)
      resolve(accessedRoutes) */
      getRoutes().then(response => {
        const list = response.data
        console.info(list)
        accessedRoutes = filterAsyncServerRolesRouter(list)
        commit('SET_ROUTES', accessedRoutes)
        resolve(accessedRoutes)
      })
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
