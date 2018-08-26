import React, {Component} from "react";

export default class FAQ extends Component {

    constructor(props) {
        super(props);
    }

    render() {

        return [<div className="col-md-10 content-section">

            <div className="title" style={{height: "5%"}}>
                <div className="description"/>
                <span className="f30sbg"> Preguntas Frecuentes</span>
                <div className="violet-line"/>
            </div>
            <div className="body d-flex flex-column" style={{height: "80%"}}>
                <div className="row">
                    <div className="col-md-12" style={{overflowY: "scroll"}}>
                        <div className="row">
                            <div className="col-md-12">
                                <span className="f20sbb">Pregunta #1</span>
                            </div>
                        </div>

                        <div className="row">
                            <div className="col-md-12">
                                <p className="question">
                                    P: Estoy tratando de registrar a mi hijo(a) y el sistema indica que no encontró
                                    registro del
                                    estudiante, no obstante, la información que introduzco es correcta.
                                </p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-12">
                                <p className="answer">
                                    R:      Los datos que está ingresando en la búsqueda del estudiante deben coincidir
                                    con
                                    los datos
                                    que están registrados en el Sistema de Información Estudiantil. Debe visitar la
                                    escuela y
                                    solicitar que los datos del estudiante sean verificados en el SIE. Entre los
                                    errores
                                    frecuentes se encuentran: Error en la fecha de nacimiento, seguro social,
                                    género,
                                    nombre y
                                    apellidos del estudiante. Aproveche para corregir y registrar la dirección
                                    física y
                                    postal
                                    de su casa y los teléfonos. Por seguridad, las correcciones o cambios en la
                                    información
                                    demográfica del estudiante deben realizarse desde la escuela.
                                </p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-12">
                                <span className="f20sbb">Pregunta #2</span>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-12">
                                <p className="question">
                                    P: Deseo cambiar a mi hijo de la escuela que seleccioné. ¿Cómo puedo editar la
                                    matrícula?
                                </p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-12">
                                <p className="answer">
                                    R:      Una vez acceda a su cuenta, en la esquina derecha superior, debajo de su
                                    nombre
                                    puede seleccionar la opción: solicitudes. Se le mostrarán los records de
                                    matrícula
                                    realizados. Oprima el ícono al lado derecho del nombre del estudiante al cual
                                    quiere
                                    editar su matrícula y realice los cambios deseados.
                                </p>
                            </div>
                        </div>

                        <div className="row">
                            <div className="col-md-12">
                                <span className="f20sbb">Pregunta #3</span>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-12">
                                <p className="question">
                                    P: Confirmé a mi hijo en una escuela del programa ocupacional cuando realmente
                                    quería matricularlo en una escuela regular.

                                </p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-12">
                                <p className="answer">
                                    R:      Habilitaremos nuevamente el récord para que seleccione la escuela de su
                                    preferencia.
                                    Envíe un correo electrónico a <a
                                    href="mailto:DE-SIE@de.pr.gov?subject=Seleccione programa incorrecto">DE-SIE@de.pr.gov.</a>.
                                    Favor de incluir el nombre del
                                    estudiante y correo electrónico utilizado en el registro. Incluya una breve
                                    descripción del problema y un número telefónico para ponernos en contacto con
                                    usted
                                    y asistirle.

                                </p>
                            </div>
                        </div>

                        <div className="row">
                            <div className="col-md-12">
                                <span className="f20sbb">Pregunta #4</span>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-12">
                                <p className="question">
                                    P: Confirmé a mi hijo en una escuela del programa regular cuando realmente
                                    quería
                                    matricularlo en una escuela ocupacional.
                                </p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-12">
                                <p className="answer">
                                    R:      Habilitaremos nuevamente el récord para que seleccione la escuela de su
                                    preferencia.
                                    Envíe un correo electrónico a <a
                                    href="mailto:DE-SIE@de.pr.gov?subject=Seleccione programa incorrecto">DE-SIE@de.pr.gov.</a>.
                                    Favor de incluir el nombre del
                                    estudiante y correo electrónico utilizado en el registro. Incluya una breve
                                    descripción del problema y un número telefónico para ponernos en contacto con
                                    usted
                                    y asistirle.
                                </p>
                            </div>
                        </div>

                        <div className="row">
                            <div className="col-md-12">
                                <span className="f20sbb">Pregunta #5</span>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-12">
                                <p className="question">
                                    P: ¿Cuál es el significado de los estatus de matrícula (sometida y aprobada)?

                                </p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-12">
                                <p className="answer">
                                    R:      Los estudiantes que ya son parte de la matrícula de una escuela reciben el
                                    estatus de aprobado una vez el padre confirme la matrícula para dicha escuela.
                                    Los
                                    estudiantes que están solicitando admisión a otra escuela o son nuevos en el
                                    sistema
                                    reciben el estatus de sometida, no están aprobados aún. Luego de culminado el
                                    período para confirmar la matrícula se realizará el proceso de validar y aprobar
                                    estas solicitudes de matrícula. El resultado de esta validación le será
                                    notificado
                                    vía correo electrónico a partir del día 14 de mayo de 2018.
                                </p>
                            </div>
                        </div>

                        <div className="row">
                            <div className="col-md-12">
                                <span className="f20sbb">Pregunta #6</span>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-12">
                                <p className="question">
                                    P: ¿Cuándo voy a saber si la matrícula de mi hijo fue aprobada?
                                </p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-12">
                                <p className="answer">
                                    R:      Las notificaciones relacionadas a las matrículas sometidas se estarán
                                    enviando a
                                    partir del 14 de mayo de 2018 vía correo electrónico.
                                </p>
                            </div>
                        </div>

                        <div className="row">
                            <div className="col-md-12">
                                <span className="f20sbb">Pregunta #7</span>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-12">
                                <p className="question">
                                    P: No se refleja en el sistema la escuela o el grado para el que quiero
                                    solicitar
                                    y/o confirmar matrícula.
                                </p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-12">
                                <p className="answer">
                                    R:      Envíe un correo electrónico a <a
                                    href="mailto:DE-SIE@de.pr.gov?subject=Grado no aparece en una escuela">DE-SIE@de.pr.gov.</a> Favor
                                    de incluir el nombre
                                    del
                                    estudiante y correo electrónico utilizado en el registro. Incluya una breve
                                    descripción del problema y un número telefónico para ponernos en contacto con
                                    usted
                                    y asistirle.

                                </p>
                            </div>
                        </div>

                        <div className="row">
                            <div className="col-md-12">
                                <span className="f20sbb">Pregunta #8</span>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-12">
                                <p className="question">
                                    P: Registré mi hijo como estudiante nuevo y él es un estudiante activo en el
                                    Departamento de Educación.
                                </p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-12">
                                <p className="answer">
                                    R:      Envíe un correo electrónico a <a
                                    href="mailto:DE-SIE@de.pr.gov?subject=Registrado como Nuevo Ingreso por error">DE-SIE@de.pr.gov.</a> Favor
                                    de incluir el nombre
                                    del
                                    estudiante y correo electrónico utilizado en el registro. Incluya una breve
                                    descripción del problema y un número telefónico para ponernos en contacto con
                                    usted
                                    y asistirle.
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>,
            <div className="col-md-1 illustration-section d-flex align-items-center text-center"/>,

        ];
    }

}