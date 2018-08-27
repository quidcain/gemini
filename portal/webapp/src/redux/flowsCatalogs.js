import * as UIHelper from "../UIHelper";
//Responses

const START = {type: "START", nextButton: UIHelper.getText("startButton")};
const QUESTION = {
    type: "YES_NO",
    nextButton: UIHelper.getText("yesButton"),
    previousButton: UIHelper.getText("noButton")
};
const NOT_FOUND_QUESTION = {
    type: "YES_NO",
    nextButton: UIHelper.getText("searchAgainButton"),
    previousButton: UIHelper.getText("createRegisterButton")
};
const SEARCH = {type: "SEARCH", nextButton: UIHelper.getText("searchButton")};
const IN_PROGRESS = {
    type: "NEXT_PREVIOUS",
    nextButton: UIHelper.getText("continueButton"),
    previousButton: UIHelper.getText("moveBackButton")
};
const CONTINUE = {type: "START", nextButton: UIHelper.getText("continueButton")};
const END = {type: "FINALIZE", nextButton: UIHelper.getText("submitButton")};
const FINALIZE_OR_CHANGE = {
    type: "FINALIZE_OR_CHANGE",
    nextButton: UIHelper.getText("submitButton"),
    previousButton: UIHelper.getText("modifyButton")
};
const ROUTE_FOOTER = {
    type: "ROUTE_FOOTER",
    nextButton: UIHelper.getText("confirmEnrollmentButton"),
    nextLabelTooltip: UIHelper.getText("tooltipConfirmEnrollmentExplanation"),
    previousButton: UIHelper.getText("newEnrollmentButton"),
    previousLabelTooltip: UIHelper.getText("tooltipNewEntryExplanation")
};


export let catalog = [
    {type: "USER_PROFILE", footerType: CONTINUE}
    , {type: "USER_ADDITIONAL_INFO", footerType: CONTINUE}
    , {
        type: "INSTRUCTIONS",
        yes: "STUDENT_LOOKUP",
        no: "PERSONAL_INFO",
        triggerAnswerOn: "DE_PROGRAM_QUESTION",
        isQuestionConditioned: true,
        footerType: ROUTE_FOOTER
    }
    , {
        type: "DEPR_ENROLLED_QUESTION",
        yes: "STUDENT_LOOKUP",
        no: "PERSONAL_INFO",
        footerType: QUESTION,
        triggerAnswerOn: "DE_PROGRAM_QUESTION",
        isQuestionConditioned: true
    }
    , {
        type: "DE_PROGRAM_QUESTION",
        footerType: QUESTION,
        yes: "DEPR_ENROLLED_QUESTION",
        no: "DEPR_ENROLLED_QUESTION",
        isQuestion: true
    }
    , {
        type: "STUDENT_LOOKUP",
        footerType: SEARCH,
        success: "FOUND_INFO",
        failure: "NOT_FOUND_QUESTION",
        waitForResult: true,
        isQuestion: true
    }
    , {
        type: "NOT_FOUND_QUESTION",
        yes: "STUDENT_LOOKUP",
        no: "PERSONAL_INFO",
        footerType: NOT_FOUND_QUESTION,
        isQuestion: true
    }
    , {type: "FOUND_INFO", footerType: CONTINUE}
    , {type: "PERSONAL_INFO", footerType: IN_PROGRESS, editFooterType: CONTINUE}
    , {type: "PERSONAL_ADDITIONAL_INFO", footerType: IN_PROGRESS, editFooterType: CONTINUE}
    , {
        type: "IS_STUDENT_HISPANIC_QUESTION",
        yes: "IS_STUDENT_BORN_PR_QUESTION",
        no: "IS_STUDENT_BORN_PR_QUESTION",
        footerType: QUESTION,
        isQuestion: true
    }
    , {type: "IS_STUDENT_BORN_PR_QUESTION", yes: "ADDRESS", no: "ADDRESS", footerType: QUESTION, isQuestion: true}

    , {
        type: "ADDRESS",
        footerType: IN_PROGRESS
    }
    , {
        type: "NEED_TRANSPORTATION_QUESTION",
        //preEnrollmentFound
        onFound: "PRE_ENROLLMENT_FOUND_SUBMIT",
        //preEnrollmentNotFound
        nextRegularOnNotFoundPreEnrollment: "PRE_ENROLLMENT_ALTERNATE_SCHOOLS_SELECTION",
        nextSpecializedOnNotFoundPreEnrollment: "PRE_ENROLLMENT_SPECIALIZED_ALTERNATE_SCHOOLS_SELECTION",
        //occupational
        nextOccupationalOnNotFoundWhenEmptySelectionPreEnrollment: "VOCATIONAL_SCHOOL_SELECTION",
        nextOccupationalOnNotFoundPreEnrollment: "VOCATIONAL_REVIEW_SUBMIT",
        nextShowEnrollmentLocation: "END_ENROLLMENT_LOCATION",
        //technical not used this page
        footerType: QUESTION,
    }, {
        type: "REASON_FOR_NOT_ATTENDING_QUESTION",
        //onPreEnrollmentFound
        nextRegular: "PRE_ENROLLMENT_ALTERNATE_SCHOOLS_SELECTION",
        nextSpecialized: "PRE_ENROLLMENT_SPECIALIZED_ALTERNATE_SCHOOLS_SELECTION",
        nextOnOutOfCountry: "END_PRE_ENROLLMENT_BY_MOVE_OUT_OF_COUNTRY",
        footerType: CONTINUE,
    },
    {
        type: "END_PRE_ENROLLMENT_BY_MOVE_OUT_OF_COUNTRY",
        next: "HOME",
        footerType: CONTINUE,

    }
    , {
        type: "PRE_ENROLLMENT_SPECIALIZED_ALTERNATE_SCHOOLS_SELECTION",
        footerType: IN_PROGRESS
    }

    , {
        type: "PRE_ENROLLMENT_ALTERNATE_SCHOOLS_SELECTION",
        footerType: IN_PROGRESS
    }
    , {
        type: "PRE_ENROLLMENT_ALTERNATE_SCHOOLS_SUBMIT",
        footerType: QUESTION,
        yes: "PRE_ENROLLMENT_COMPLETED",
        noRegular: "PRE_ENROLLMENT_ALTERNATE_SCHOOLS_SELECTION",
        noSpecialized: "PRE_ENROLLMENT_SPECIALIZED_ALTERNATE_SCHOOLS_SELECTION",
        isSubmit: true

    }
    , {
        type: "PRE_ENROLLMENT_FOUND_SUBMIT",
        footerType: QUESTION,
        yes: "PRE_ENROLLMENT_COMPLETED",
        // no: "REASON_FOR_NOT_ATTENDING_QUESTION",
        //onPreEnrollmentFound
        noRegular: "PRE_ENROLLMENT_ALTERNATE_SCHOOLS_SELECTION",
        noSpecialized: "PRE_ENROLLMENT_SPECIALIZED_ALTERNATE_SCHOOLS_SELECTION",
        noOccupational: "VOCATIONAL_SCHOOL_SELECTION",
        isSubmit: true
    }

    , {type: "PRE_ENROLLMENT_COMPLETED", footerType: CONTINUE}
    , {type: "PRE_ENROLLMENT_CONFIRMED", footerType: QUESTION, yes: "INSTRUCTIONS", no: "HOME"}


    , {type: "TECHNICAL_SCHOOL_SELECTION", footerType: CONTINUE}
    , {type: "VOCATIONAL_SCHOOL_SELECTION", footerType: CONTINUE}
    , {type: "VOCATIONAL_SCHOOL_INFO", footerType: CONTINUE}
    , {type: "VOCATIONAL_PROGRAMS", footerType: CONTINUE}
    , {
        type: "VOCATIONAL_REVIEW_SUBMIT",
        footerType: QUESTION,
        yes: "PRE_ENROLLMENT_COMPLETED",
        no: "VOCATIONAL_SCHOOL_SELECTION",
        addingSchoolHop: "VOCATIONAL_SCHOOL_SELECTION",
        editSchoolHop: "VOCATIONAL_PROGRAMS",
        isQuestion: true
    }

    , {type: "END_ENROLLMENT_LOCATION", footerType: CONTINUE}
];
