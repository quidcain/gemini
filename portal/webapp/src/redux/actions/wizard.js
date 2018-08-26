import * as types from "../types";
import * as catalogs from "../flowsCatalogs";

let flow;  //working flow
let catalog = catalogs.catalog;

//escuelas regulares
const normalFlow = [
    getIndexFromCatalog("USER_PROFILE"),
    getIndexFromCatalog("INSTRUCTIONS"),
    getIndexFromCatalog("DE_PROGRAM_QUESTION"),
    getIndexFromCatalog("STUDENT_LOOKUP"),
    getIndexFromCatalog("NOT_FOUND_QUESTION"),
    getIndexFromCatalog("FOUND_INFO"),
    getIndexFromCatalog("PERSONAL_INFO"),

    getIndexFromCatalog("ADDRESS"),
    getIndexFromCatalog("NEED_TRANSPORTATION_QUESTION"),
    getIndexFromCatalog("PRE_ENROLLMENT_ALTERNATE_SCHOOLS_SELECTION"),
    getIndexFromCatalog("PRE_ENROLLMENT_ALTERNATE_SCHOOLS_SUBMIT"),
    getIndexFromCatalog("PRE_ENROLLMENT_FOUND_SUBMIT"),
    getIndexFromCatalog("PRE_ENROLLMENT_COMPLETED"),
    getIndexFromCatalog("PRE_ENROLLMENT_CONFIRMED")];
const editNormalFlow = [
    getIndexFromCatalog("PERSONAL_INFO"),
    getIndexFromCatalog("ADDRESS"),
    getIndexFromCatalog("NEED_TRANSPORTATION_QUESTION"),
    getIndexFromCatalog("PRE_ENROLLMENT_ALTERNATE_SCHOOLS_SELECTION"),
    getIndexFromCatalog("PRE_ENROLLMENT_ALTERNATE_SCHOOLS_SUBMIT"),
    getIndexFromCatalog("PRE_ENROLLMENT_FOUND_SUBMIT"),
    getIndexFromCatalog("PRE_ENROLLMENT_COMPLETED"),
    getIndexFromCatalog("PRE_ENROLLMENT_CONFIRMED")
];

//escuelas especializadas
const specializedFlow = [
    getIndexFromCatalog("USER_PROFILE"),
    getIndexFromCatalog("INSTRUCTIONS"),
    getIndexFromCatalog("DE_PROGRAM_QUESTION"),
    getIndexFromCatalog("STUDENT_LOOKUP"),
    getIndexFromCatalog("NOT_FOUND_QUESTION"),
    getIndexFromCatalog("FOUND_INFO"),
    getIndexFromCatalog("PERSONAL_INFO"),
    getIndexFromCatalog("ADDRESS"),
    getIndexFromCatalog("NEED_TRANSPORTATION_QUESTION"),
    getIndexFromCatalog("PRE_ENROLLMENT_SPECIALIZED_ALTERNATE_SCHOOLS_SELECTION"),

    getIndexFromCatalog("PRE_ENROLLMENT_ALTERNATE_SCHOOLS_SUBMIT"),
    getIndexFromCatalog("PRE_ENROLLMENT_FOUND_SUBMIT"),

    getIndexFromCatalog("PRE_ENROLLMENT_COMPLETED"),
    getIndexFromCatalog("PRE_ENROLLMENT_CONFIRMED")];
const specializedEditFlow = [
    getIndexFromCatalog("PERSONAL_INFO"),
    getIndexFromCatalog("ADDRESS"),
    getIndexFromCatalog("NEED_TRANSPORTATION_QUESTION"),
    getIndexFromCatalog("PRE_ENROLLMENT_SPECIALIZED_ALTERNATE_SCHOOLS_SELECTION"),
    // it is confused
    getIndexFromCatalog("PRE_ENROLLMENT_ALTERNATE_SCHOOLS_SUBMIT"),
    getIndexFromCatalog("PRE_ENROLLMENT_FOUND_SUBMIT"),

    getIndexFromCatalog("PRE_ENROLLMENT_COMPLETED"),
    getIndexFromCatalog("PRE_ENROLLMENT_CONFIRMED")
];

//escuelas ocupacionales
const occupationalFlow = [
    getIndexFromCatalog("USER_PROFILE"),
    getIndexFromCatalog("INSTRUCTIONS"),
    getIndexFromCatalog("DE_PROGRAM_QUESTION"),
    getIndexFromCatalog("STUDENT_LOOKUP"),
    getIndexFromCatalog("NOT_FOUND_QUESTION"),
    getIndexFromCatalog("FOUND_INFO"),
    getIndexFromCatalog("PERSONAL_INFO"),
    getIndexFromCatalog("ADDRESS"),
    getIndexFromCatalog("NEED_TRANSPORTATION_QUESTION"),
    getIndexFromCatalog("PRE_ENROLLMENT_FOUND_SUBMIT"),
    getIndexFromCatalog("VOCATIONAL_SCHOOL_SELECTION"),
    getIndexFromCatalog("VOCATIONAL_SCHOOL_INFO"),
    getIndexFromCatalog("VOCATIONAL_PROGRAMS"),
    getIndexFromCatalog("VOCATIONAL_REVIEW_SUBMIT"),
    getIndexFromCatalog("PRE_ENROLLMENT_COMPLETED"),
    getIndexFromCatalog("PRE_ENROLLMENT_CONFIRMED")
];
const editOccupationalFlow = [
    getIndexFromCatalog("PERSONAL_INFO"),
    getIndexFromCatalog("ADDRESS"),
    getIndexFromCatalog("NEED_TRANSPORTATION_QUESTION"),
    getIndexFromCatalog("PRE_ENROLLMENT_FOUND_SUBMIT"),

    getIndexFromCatalog("VOCATIONAL_SCHOOL_SELECTION"),
    getIndexFromCatalog("VOCATIONAL_SCHOOL_INFO"),
    getIndexFromCatalog("VOCATIONAL_PROGRAMS"),
    getIndexFromCatalog("VOCATIONAL_REVIEW_SUBMIT"),
    getIndexFromCatalog("PRE_ENROLLMENT_COMPLETED"),
    getIndexFromCatalog("PRE_ENROLLMENT_CONFIRMED")
];

//institutos tecnicos
const technicalFlow = [
    getIndexFromCatalog("USER_PROFILE"),
    getIndexFromCatalog("INSTRUCTIONS"),
    getIndexFromCatalog("DE_PROGRAM_QUESTION"),
    getIndexFromCatalog("STUDENT_LOOKUP"),
    getIndexFromCatalog("NOT_FOUND_QUESTION"),
    getIndexFromCatalog("FOUND_INFO"),
    //verify this
    getIndexFromCatalog("PERSONAL_INFO"),
    getIndexFromCatalog("ADDRESS"),

    getIndexFromCatalog("TECHNICAL_SCHOOL_SELECTION"),
    getIndexFromCatalog("VOCATIONAL_SCHOOL_INFO"),
    getIndexFromCatalog("VOCATIONAL_PROGRAMS"),
    getIndexFromCatalog("VOCATIONAL_REVIEW_SUBMIT"),
    getIndexFromCatalog("PRE_ENROLLMENT_COMPLETED"),
    getIndexFromCatalog("PRE_ENROLLMENT_CONFIRMED")
];
const editTechnicalFlow = [
    getIndexFromCatalog("PERSONAL_INFO"),
    getIndexFromCatalog("ADDRESS"),

    getIndexFromCatalog("TECHNICAL_SCHOOL_SELECTION"),
    getIndexFromCatalog("VOCATIONAL_SCHOOL_INFO"),
    getIndexFromCatalog("VOCATIONAL_PROGRAMS"),
    getIndexFromCatalog("VOCATIONAL_REVIEW_SUBMIT"),
    getIndexFromCatalog("PRE_ENROLLMENT_COMPLETED"),
    getIndexFromCatalog("PRE_ENROLLMENT_CONFIRMED")
];

function getIndexFromCatalog(type) {
    for (let idx in catalog) {
        let page = catalog[idx];
        if (page.type === type) {
            return parseInt(idx);
        }
    }
}

function getIndexFromFlow(type) {
    for (let idx in flow) {
        let pageIdx = flow[idx];
        let page = catalog[pageIdx];
        if (page.type === type) {
            return parseInt(idx);
        }
    }
}

function getForm(current) {
    let index = flow[current];
    return catalog[index];
}

function isType(current, type) {
    let index = flow[current];
    return catalog[index].type === type;
}

function exists(type) {
    for (let idx in flow) {
        let pageIdx = flow[idx];
        let page = catalog[pageIdx];
        if (page.type === type)
            return true;
    }
    return false;
}

function changeForms(dispatch, formToChange) {
    flow = formToChange;
    dispatch({type: types.ON_WIZARD_FORMS_CHANGE, forms: formToChange});
}

function changeToRegularForm(dispatch, preEnrollment, formToChange) {
    preEnrollment.type = types.REGULAR_ENROLLMENT;
    changeForms(dispatch, formToChange)
}

function changeToSpecializedForm(dispatch, preEnrollment, formToChange) {
    preEnrollment.type = types.SPECIALIZED_SCHOOLS_ENROLLMENT;
    changeForms(dispatch, formToChange)
}

function changeToOccupationalForm(dispatch, preEnrollment, formToChange) {
    preEnrollment.type = types.OCCUPATIONAL_ENROLLMENT;
    changeForms(dispatch, formToChange)
}

function changeToTechniqueForm(dispatch, preEnrollment, formToChange) {
    preEnrollment.type = types.TECHNIQUE_ENROLLMENT;
    changeForms(dispatch, formToChange)
}

function conditionedQuestionAnswer(dispatch, currentForm, answer) {
    if (currentForm.isQuestionConditioned) {
        dispatch({
            type: types.ON_WIZARD_CONDITIONED_QUESTION,
            answer: {answer: answer, type: currentForm.type, trigger: currentForm.trigger}
        })
    }
}

export const load = (requestId) => (dispatch, getState) => {
    dispatch({type: types.ON_WIZARD_LOAD_START});
    let user = getState().profile.user;
    let profileCompleted = user.profileCompleted;
    let editing = requestId;

    flow = editing
        ? editNormalFlow
        : normalFlow;  //always start here
    let startPage = profileCompleted ? getIndexFromFlow("INSTRUCTIONS") : 0;
    if (editing) {
        startPage = 0;
        requestId = requestId || user.workingPreEnrollmentId;
    }

    let startForm = getForm(startPage);
    let footerType = editing && startForm.editFooterType
        ? startForm.editFooterType
        : startForm.footerType;
    //check if user has pending pre-enrollment
    dispatch({
        type: types.ON_WIZARD_LOAD_END,
        current: startPage,
        footerType: footerType,
        pageType: startForm.type,
        editing: editing,
        formsToDisplay: flow,
        workingRequestId: requestId
    })
};

export const goToAction = (type) => (dispatch) => {
    if (exists(type)) {
        let next = getIndexFromFlow(type);
        let nextForm = getForm(next);
        dispatch({
            type: types.ON_WIZARD_GO_TO,
            current: next,
            footerType: nextForm.footerType,
            pageType: nextForm.type
        });
    }
};

function isOccupationalOrTechnicalFlow(preEnrollment) {
    return preEnrollment.type === types.OCCUPATIONAL_ENROLLMENT || preEnrollment.type === types.TECHNIQUE_ENROLLMENT;
}

function isOccupational(preEnrollment) {
    return preEnrollment.type === types.OCCUPATIONAL_ENROLLMENT;
}

function isRegularOrSpecializedFlow(preEnrollment) {
    return (preEnrollment.type === types.REGULAR_ENROLLMENT || preEnrollment.type === types.REGULAR_ALTERNATE_SCHOOLS || preEnrollment.type === types.SPECIALIZED_SCHOOLS_ENROLLMENT)
}

function nextOnTransportationPage(preEnrollment, currentForm) {
    let preEnrollmentInfo = preEnrollment.info;
    if (!preEnrollmentInfo.canGoToFoundEnrollment) {
        let onNotFound;
        switch (preEnrollment.type) {
            case types.SPECIALIZED_SCHOOLS_ENROLLMENT:
                onNotFound = currentForm.nextSpecializedOnNotFoundPreEnrollment;
                break;
            case types.REGULAR_ALTERNATE_SCHOOLS:
                onNotFound = currentForm.nextRegularOnNotFoundPreEnrollment;
                break;
            case types.OCCUPATIONAL_ENROLLMENT:
                onNotFound = !preEnrollmentInfo.hasSchoolsSelected
                    ? currentForm.nextOccupationalOnNotFoundWhenEmptySelectionPreEnrollment
                    : currentForm.nextOccupationalOnNotFoundPreEnrollment;
                break;
            default:
                onNotFound = currentForm.nextRegularOnNotFoundPreEnrollment;

        }
        return getIndexFromFlow(onNotFound)
    } else {
        return getIndexFromFlow(currentForm.onFound);
    }
}

function nextOnReasonPage(preEnrollment, currentForm) {
    let isSpecializedFlow = preEnrollment.type === types.SPECIALIZED_SCHOOLS_ENROLLMENT;
    let next = isSpecializedFlow ? currentForm.nextSpecialized : currentForm.nextRegular;
    return getIndexFromFlow(next);
}

function nextOnSubmitPage(preEnrollment, currentForm) {
    let isSpecializedFlow = preEnrollment.type === types.SPECIALIZED_SCHOOLS_ENROLLMENT;
    let next = isSpecializedFlow ? currentForm.noNextSpecialized : currentForm.noNextRegular;
    return getIndexFromFlow(next);
}

export const onNextAction = (onPress) => (dispatch, getState) => {
    dispatch({type: types.ON_WIZARD_NEXT_START});
    let wizard = getState().wizard;
    let preEnrollment = getState().preEnrollment;
    let student = getState().studentInfo.student;
    let user = getState().profile.user;

    let current = wizard.current;
    let maxForms = wizard.maxForms;
    let maxCurrent = (maxForms - 1);


    //changing forms
    if (isType(current, "PERSONAL_INFO") && wizard.editing) {
        // !user.userVocationalStudent
        changeFormFlow(preEnrollment.type, dispatch, preEnrollment, true);
    }

    let currentForm = getForm(current);
    let next = current + 1;

    conditionedQuestionAnswer(dispatch, currentForm, currentForm.yes);

    if (currentForm.isQuestion || currentForm.isSubmit) {
        next = getIndexFromFlow(currentForm.yes);
    } else if (isType(current, "NEED_TRANSPORTATION_QUESTION") && (isRegularOrSpecializedFlow(preEnrollment) || isOccupational(preEnrollment))) {
        next = nextOnTransportationPage(preEnrollment, currentForm)
    } else if (isType(current, "REASON_FOR_NOT_ATTENDING_QUESTION") && isRegularOrSpecializedFlow(preEnrollment)) {
        next = nextOnReasonPage(preEnrollment, currentForm);
    } else if (isType(current, "END_PRE_ENROLLMENT_BY_MOVE_OUT_OF_COUNTRY") || isType(current, "PRE_ENROLLMENT_CONFIRMED")) {
        resetWizard()(dispatch);
    }


    onPress((result) => {
        if (currentForm.waitForResult && result) {

            next = result === types.ON_FOUND_CALLBACK
                ? getIndexFromFlow(currentForm.success)
                : getIndexFromFlow(currentForm.failure);
        }
        student = getState().studentInfo.student;
        if (student.existsOnSie && isType(current, "PERSONAL_INFO")) {
            next = getIndexFromFlow("ADDRESS");
        }

        preEnrollment = getState().preEnrollment;
        let isOutOfCountry = preEnrollment.reasonSaved && preEnrollment.reasonSelected === types.MOVE_OUT_OF_COUNTRY;
        if (isOutOfCountry && isType(current, "REASON_FOR_NOT_ATTENDING_QUESTION"))
            next = getIndexFromFlow(currentForm.nextOnOutOfCountry);
        else if (isType(current, "END_PRE_ENROLLMENT_BY_MOVE_OUT_OF_COUNTRY")) {
            dispatch({type: types.ON_WIZARD_COMPLETED, startOver: false});
            return;
        }

         maxCurrent = current;
        if (maxCurrent === current) {
            dispatch({type: types.ON_WIZARD_COMPLETED, startOver: false});
        } else {
            let nextForm = getForm(next);
            dispatch({
                type: types.ON_WIZARD_NEXT_END,
                current: current < maxCurrent ? next : maxCurrent,
                isFinalStep: maxCurrent === current,
                footerType: nextForm.footerType,
                pageType: nextForm.type
            });
        }
    })
};

function previousOnSubmit(preEnrollment, currentForm) {
    let next;
    switch (preEnrollment.type) {
        case types.SPECIALIZED_SCHOOLS_ENROLLMENT:
            next = currentForm.noSpecialized;
            break;
        case types.REGULAR_ALTERNATE_SCHOOLS:
            next = currentForm.noRegular;
            break;
        case types.OCCUPATIONAL_ENROLLMENT:
            next = currentForm.noOccupational;
            break;
        default:
            next = currentForm.noRegular;

    }

    return getIndexFromFlow(next);

}

export const onPreviousAction = (onPress) => (dispatch, getState) => {
    dispatch({type: types.ON_WIZARD_PREVIOUS_START});
    let preEnrollment = getState().preEnrollment;
    let wizard = getState().wizard;
    let flowNavigation = wizard.flowNavigation;
    let current = wizard.current;
    let maxForms = wizard.maxForms;
    let maxCurrent = (maxForms - 1);

    let currentForm = getForm(current);
    let next;
    conditionedQuestionAnswer(dispatch, currentForm, currentForm.no);

    if (currentForm.isQuestion) {
        next = getIndexFromFlow(currentForm.no);
    } else if (currentForm.isQuestionConditioned) {
        next = getIndexFromFlow(currentForm.triggerAnswerOn);
    } else if (currentForm.isSubmit) {
        next = previousOnSubmit(preEnrollment, currentForm)
    } else if (isType(current, "NEED_TRANSPORTATION_QUESTION")) {
        next = nextOnTransportationPage(preEnrollment, currentForm);
    } else {
        next = flowNavigation.length > 0
            ? pop(flowNavigation, 2)
            : current - 1;
    }
    let nextForm = getForm(next);

    onPress((result) => {
        maxCurrent = current;

        if (maxCurrent === current) {
            dispatch({type: types.ON_WIZARD_COMPLETED, startOver: false});
        } else {
            dispatch({
                type: types.ON_WIZARD_PREVIOUS_END,
                current: next,
                footerType: nextForm.footerType,
                pageType: nextForm.type
            });
        }
    });

};

function changeFormFlow(selection, dispatch, preEnrollment, edited = false) {
    switch (selection) {
        case types.OCCUPATIONAL_ENROLLMENT:
            changeToOccupationalForm(dispatch, preEnrollment, edited ? editOccupationalFlow : occupationalFlow);
            break;
        case types.TECHNIQUE_ENROLLMENT:
            changeToTechniqueForm(dispatch, preEnrollment, edited ? editTechnicalFlow : technicalFlow);
            break;
        case types.REGULAR_ENROLLMENT:
        case types.REGULAR_ALTERNATE_SCHOOLS:
            changeToRegularForm(dispatch, preEnrollment, edited ? editNormalFlow : normalFlow);
            break;
        case types.SPECIALIZED_SCHOOLS_ENROLLMENT:
            changeToSpecializedForm(dispatch, preEnrollment, edited ? specializedEditFlow : specializedFlow);
            break;
    }

}

export const onProgramSelectionAction = (selection) => (dispatch, getState) => {
    // selection can be: TECHNIQUE, OCCUPATIONAL, REGULAR
    dispatch({type: types.ON_WIZARD_NEXT_START});
    let preEnrollment = getState().preEnrollment;
    let wizard = getState().wizard;
    let current = wizard.current;
    let next = current + 1;
    let lastQuestion = wizard.lastQuestion;

    changeFormFlow(selection, dispatch, preEnrollment);

    if (lastQuestion && lastQuestion.answer) {
        next = getIndexFromFlow(lastQuestion.answer)
    }

    let nextForm = getForm(next);
    dispatch({
        type: types.ON_WIZARD_NEXT_END,
        current: next,
        isFinalStep: false,
        footerType: nextForm.footerType,
        pageType: nextForm.type
    });

};

export const onBackAction = (appBackAction) => (dispatch, getState) => {
    let wizard = getState().wizard;
    let flowNavigation = wizard.flowNavigation;
    if (flowNavigation.length > 1) {
        dispatch({type: types.ON_WIZARD_PREVIOUS_START});
        let next = pop(flowNavigation, 2);
        let nextForm = getForm(next);
        dispatch({
            type: types.ON_WIZARD_PREVIOUS_END,
            current: next,
            footerType: nextForm.footerType,
            pageType: nextForm.type
        });
    } else {
        if (appBackAction)
            appBackAction();
    }
};

export const resetWizard = () => (dispatch) => {
    dispatch({type: types.ON_WIZARD_RESET});
    dispatch({type: types.CANCEL_BLOCK_UI});
};

function pop(array, n) {
    let val;
    while (n > 0) {
        val = array.pop();
        n--;
    }
    return val;
}
