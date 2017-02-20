import React from 'react';
import { shallow } from 'enzyme';
import {expect} from 'chai';
import RequestForm from '../scripts/js/login.jsx';

describe('<RequestForm />', () => {
    it('renders Request Form', () => {
        const wrapper = shallow(<RequestForm/>);
        expect(wrapper.find('FormGroup')).to.have.length(4);
        expect(wrapper.find('FormControl')).to.have.length(2);
    });
});
