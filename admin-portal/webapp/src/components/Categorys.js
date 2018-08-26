import React from 'react'
import PropTypes from 'prop-types'

class Categorys extends React.Component {
    state = {
        isVisible: false
    }

    render() {
        const {
            category,
            value,
            icon,
            smallIcon,
            smallIconColor,
            iconDesc,
            iconDescColor,
            iconAction,
            markColor
        } = this.props
        return (
            <div className='com-category col-md-12 content-section'>
                <div className='category-main-content'>
                    <div className='cat-title'>{category}</div>
                    <div className='cat-value'>{value}</div>
                    <div className='cat-div' />
                    <div className='cat-bottom'>
                        <i className={smallIcon} style={{color: smallIconColor, paddingRight: '5px'}} />
                        <div className='cat-icondesc' style={{color: iconDescColor}}>{iconDesc}</div>
                    </div>
                </div>
                <div className='category-main-mark'>
                    <div className='mark-container' style={{background: markColor}}>
                        <div className='aspect-resize'></div>
                    </div>
                    <i className={icon} style={{color: 'white'}}/>
                </div>
            </div>
        )
    }
}

export default Categorys
