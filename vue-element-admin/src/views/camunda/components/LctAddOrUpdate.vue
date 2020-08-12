<template>
  <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" width="90%" top="3vh" :before-close="handleClose">
    <div ref="content" class="containersLct">
      <div ref="canvas" class="canvasLct" />
      <div id="js-properties-panel" class="panelLct" />
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button @click="handleClose">
        取消
      </el-button>
      <el-button type="primary" @click="postData">
        确定
      </el-button>
    </div>
  </el-dialog>
</template>

<script>
import waves from '@/directive/waves'
import { fblc, lct } from '@/api/gzl'
import BpmnModeler from 'bpmn-js/lib/Modeler'
import propertiesPanelModule from 'bpmn-js-properties-panel'
import propertiesProviderModule from 'bpmn-js-properties-panel/lib/provider/camunda'
import camundaModdleDescriptor from 'camunda-bpmn-moddle/resources/camunda'
import customTranslateModule from '../../../components/bpmn-config/customTranslate'
import 'bpmn-js/dist/assets/diagram-js.css'
import 'bpmn-js/dist/assets/bpmn-font/css/bpmn.css'
import 'bpmn-js/dist/assets/bpmn-font/css/bpmn-codes.css'
import 'bpmn-js/dist/assets/bpmn-font/css/bpmn-embedded.css'
import 'bpmn-js-properties-panel/dist/assets/bpmn-js-properties-panel.css'

export default {
  name: 'LctAddOrUpdate',
  components: {},
  directives: { waves },
  data() {
    return {
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑',
        create: '添加'
      },
      temp: {
        id: undefined,
        key: undefined,
        name: undefined
      },
      formData: {
        bpmnXml: undefined
      },
      bpmnModeler: undefined,
      container: undefined,
      canvas: undefined,
      xmlStr: undefined,
      processName: undefined,
      bpmnXmlStr: undefined,
      defaultBpmnXml: '<?xml version="1.0" encoding="UTF-8"?>' +
        '<bpmn2:definitions xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:bpmn2="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" xmlns:dc="http://www.omg.org/spec/DD/20100524/DC" xmlns:di="http://www.omg.org/spec/DD/20100524/DI" xsi:schemaLocation="http://www.omg.org/spec/BPMN/20100524/MODEL BPMN20.xsd" id="sample-diagram" targetNamespace="http://bpmn.io/schema/bpmn">' +
        '  <bpmn2:process id="" name="" isExecutable="true">' +
        '  </bpmn2:process>' +
        '  <bpmndi:BPMNDiagram id="BPMNDiagram">' +
        '    <bpmndi:BPMNPlane id="BPMNPlane" bpmnElement="Process">' +
        '    </bpmndi:BPMNPlane>' +
        '  </bpmndi:BPMNDiagram>' +
        '</bpmn2:definitions>'
    }
  },
  methods: {
    async createNewDiagram() {
      // 获取到属性ref为“content”的dom节点
      this.container = this.$refs.content
      // 获取到属性ref为“canvas”的dom节点
      const canvas = this.$refs.canvas
      // 建模，官方文档这里讲的很详细
      this.bpmnModeler = new BpmnModeler({
        container: canvas,
        // 添加控制板
        propertiesPanel: {
          parent: '#js-properties-panel'
        },
        additionalModules: [
          // 左边工具栏以及节点
          propertiesProviderModule,
          // 右边的工具栏
          propertiesPanelModule,
          {
            translate: ['value', customTranslateModule]
          }
        ],
        moddleExtensions: {
          camunda: camundaModdleDescriptor
        }
      })
      const _this = this
      this.bpmnModeler.on('commandStack.changed', function() {
        _this.setEncoded()
      })
      // 将字符串转换成图显示出来
      await this.bpmnModeler.importXML(this.bpmnXmlStr)
    },
    async setEncoded() {
      const { xml } = await this.bpmnModeler.saveXML({ format: true })
      this.formData.bpmnXml = xml
    },
    resetTemp() {
      this.temp = {
        id: undefined,
        key: undefined,
        name: undefined
      }
      this.formData = {
        bpmnXml: undefined
      }
      this.bpmnModeler = undefined
      this.container = undefined
      this.canvas = undefined
      this.xmlStr = undefined
      this.processName = undefined
      this.bpmnXmlStr = undefined
    },
    getDetailsFn() {
      this.dialogFormVisible = true
      if (this.temp.id != null) {
        this.getLct()
      } else {
        this.bpmnXmlStr = this.defaultBpmnXml
        this.$nextTick(() => {
          this.createNewDiagram()
        })
      }
    },
    handleClose() {
      this.dialogFormVisible = false
      this.resetTemp()
    },
    async postData() {
      await fblc(this.formData)
      this.resetTemp()
      this.$emit('afterLctAddOrUpdate')
      this.dialogFormVisible = false
      this.$notify({
        title: '成功',
        message: '成功',
        type: 'success',
        duration: 2000
      })
    },
    async getLct() {
      const { data } = await lct({ processDefinitionId: this.temp.id })
      this.bpmnXmlStr = data
      this.$nextTick(() => {
        this.createNewDiagram()
      })
    }
  }
}
</script>

<style scoped>
  .containersLct{
    position: relative;
    background-color: #ffffff;
    width: 100%;
    height: 950px;
  }
  .canvasLct{
    width: 100%;
    height: 100%;
  }
  .panelLct{
    position: absolute;
    right: 0;
    top: 0;
    width: 300px;
  }
</style>
