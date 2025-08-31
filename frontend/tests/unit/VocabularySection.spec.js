import { shallowMount } from '@vue/test-utils'
import VocabularySection from '@/components/VocabularySection.vue'

describe('VocabularySection.vue', () => {
  it('renders the component', () => {
    const wrapper = shallowMount(VocabularySection, {
      propsData: {
        type: 'Test Type',
        requestType: 'test'
      }
    })
    expect(wrapper.exists()).toBe(true)
  })

  it('displays the correct type', () => {
    const wrapper = shallowMount(VocabularySection, {
      propsData: {
        type: 'Test Type',
        requestType: 'test'
      }
    })
    expect(wrapper.text()).toContain('Test Type')
  })
})
