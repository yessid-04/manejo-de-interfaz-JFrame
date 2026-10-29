LABORATORIO CLÍNICO - REGISTRO DE PACIENTES

Ejecución
1. Abra la carpeta LaboratorioClinico como proyecto Java en NetBeans.
2. Ejecute el proyecto (clase principal: laboratorioclinico.LaboratorioClinico).
3. El administrador registra los datos desde el formulario.

Persistencia
- Los pacientes se mantienen en un ArrayList durante la ejecución.
- Cada registro se guarda en data/pacientes.csv (UTF-8); al iniciar, el sistema carga el CSV.
- El CSV contiene datos personales y de contacto, EPS, régimen, ocupación, estado civil,
  indicador de condición médica y observaciones.

Validaciones principales
- Campos obligatorios completos.
- Documento con formato válido y único por tipo y número.
- Fecha válida, no futura y edad máxima de 120 años.
- Validación de nombres, apellidos, teléfonos y correo cuando se proporciona.
- Límites de longitud para dirección, ocupación y observaciones.
- Si falla la escritura del CSV, el registro no se conserva en la lista en memoria.

Nota: el proyecto entregado no tenía autenticación implementada. Por ello, esta versión presenta
el formulario de registro para el operador administrativo, pero no simula un inicio de sesión.
