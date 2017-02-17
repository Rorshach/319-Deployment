/**
 * Created by alekhrycaiko on 2017-02-16.
 */
/**
 * Created by alekhrycaiko on 2017-02-16.
import React from 'react';
import { shallow } from 'enzyme';
import {expect} from 'chai';
import {RequestForm} from '../scripts/js/content.jsx';

describe('<RequestForm />', () => {
    it('renders Contents RequestForm Component', () => {
        const wrapper = shallow(<RequestForm/>);
        console.log(wrapper.find('FormGroup').length);
        expect(wrapper.find('FormGroup').length > 0).to.be.true;
    });
});
 */