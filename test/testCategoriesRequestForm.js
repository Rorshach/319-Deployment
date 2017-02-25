import React from 'react';
import { shallow } from 'enzyme';
import {expect} from 'chai';
import CategoriesRequestForm from '../scripts/js/categoriesrequestform.jsx';

describe('<CategoriesRequestForm />', () => {
    it('renders Categories Request Form', () => {
        const wrapper = shallow(<CategoriesRequestForm/>);
        expect(wrapper.find('.categoriesResponse')).to.have.length(1);
        expect(wrapper.find('.categoryResponse')).to.have.length(1);
        expect(wrapper.find('.selectedItems')).to.have.length(1);
        expect(wrapper.find('.description')).to.have.length(1);
        expect(wrapper.find('.submitBtn')).to.have.length(1);
    });
});
