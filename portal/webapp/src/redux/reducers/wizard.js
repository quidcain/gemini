import * as types from "../types";
import * as Utils from "../../Utils";

const initialState = {
    lastQuestion: {answer: null, type: null, trigger: null}, //can be yes or no that means the next page and type is the page inside the catalog
    current: 0,
    currentPageType: null,
    initForm: false,
    isFinalStep: false,
    previousLabel: null,
    nextLabel: null,
    previousLabelTooltip: null,
    nextLabelTooltip: null,
    percentage: 0,
    maxForms: 11,
    flowNavigation: [0],
    wizardCompleted: false,
    editing: false,
    formsToDisplay: [],
    workingRequestId: null,
    startOver: false
};

const accountablePercentageForm = 4;

function calculatePercentage(current) {
    return Math.floor(((current - 1) / accountablePercentageForm) * 100);
}

const wizard = (state = Utils.freezeObject(initialState), action) => {
    let btnType = action.footerType;
    switch (action.type) {
        case types.ON_WIZARD_LOAD_START:
            return state;
        case types.ON_WIZARD_LOAD_END:
            return {
                ...state,
                flowNavigation: [action.current],
                current: action.current,
                previousLabel: btnType.previousButton,
                nextLabel: btnType.nextButton,
                nextLabelTooltip: btnType.nextLabelTooltip,
                previousLabelTooltip: btnType.previousLabelTooltip,
                currentPageType: action.pageType,
                maxForms: action.formsToDisplay.length,
                editing: action.editing,
                formsToDisplay: action.formsToDisplay,
                workingRequestId: action.workingRequestId

            };
        case types.ON_WIZARD_NEXT_START:
        case types.ON_WIZARD_PREVIOUS_START:
            return state;
        case types.ON_WIZARD_NEXT_END:
            state.flowNavigation.push(action.current);
            return {
                ...state,
                current: action.current,
                isFinalStep: action.isFinalStep,
                percentage: calculatePercentage(action.current),
                previousLabel: btnType.previousButton,
                nextLabel: btnType.nextButton,
                nextLabelTooltip: btnType.nextLabelTooltip,
                previousLabelTooltip: btnType.previousLabelTooltip,
                currentPageType: action.pageType,
            };
        case types.ON_WIZARD_GO_TO:
            return {
                ...state,
                current: action.current,
                previousLabel: btnType.previousButton,
                nextLabel: btnType.nextButton,
                nextLabelTooltip: btnType.nextLabelTooltip,
                previousLabelTooltip: btnType.previousLabelTooltip,
                currentPageType: action.pageType,
            };
        case types.ON_WIZARD_PREVIOUS_END:
            state.flowNavigation.push(action.current);
            return {
                ...state,
                current: action.current,
                isFinalStep: false,
                percentage: calculatePercentage(action.current),
                previousLabel: btnType.previousButton,
                nextLabel: btnType.nextButton,
                nextLabelTooltip: btnType.nextLabelTooltip,
                previousLabelTooltip: btnType.previousLabelTooltip,
                currentPageType: action.pageType,
            };
        case types.ON_WIZARD_COMPLETED:
            return {...state, wizardCompleted: true, startOver: action.startOver};
        case types.ON_WIZARD_RESET:
            return Utils.freezeObject(initialState);
        case types.ON_WIZARD_FORMS_CHANGE:
            return {...state, formsToDisplay: action.forms, maxForms: action.forms.length};
        case types.ON_WIZARD_CONDITIONED_QUESTION:
            return {...state, lastQuestion: action.answer};
        default:
            return state;

    }
};

export default wizard;