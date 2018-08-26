/**
 * Created by fran on 1/30/18.
 */
import React, {Component} from "react";
import TextInput from "../../components/TextInput";
import CurrencyInput from "../../components/CurrencyInput";
import CodeSelect from "../../components/CodeSelect";

export default class FinancialFamilyInfo extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (<form>
            <div className="row">
                <div className="col-md-4">
                    <CodeSelect id="incomeType"
                                codeType="incomeTypes"
                                label="Tipo de ingreso"
                                placeholder="Seleccione tipo de ingreso"/>
                </div>
                <div className="col-md-4">
                    <CurrencyInput id="annualIncomeOthers" placeholder="Otros Totales de ingresos"/>
                </div>
                <div className="col-md-4">
                    <CurrencyInput id="financialAidAmount" placeholder="Total de Ayuda Recibida"/>
                </div>
            </div>

            <div className="row">
                <div className="col-md-6">
                    <TextInput id="familyTotalMembers" type="number" placeholder="Total de Integrantes en la Familia"
                               disabled/>
                </div>
                <div className="col-md-6">
                    <CurrencyInput id="familyTotalIncome" placeholder="Ingreso Total Anual" disabled/>
                </div>
            </div>
        </form>);
    }
}
