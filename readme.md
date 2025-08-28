
📘 Visit API Endpoints

| **Endpoint**                        | **Method** | **Description**                                                                                      | **Input**                                                                           | **Output**                                                                      |
| ----------------------------------- | ---------- | ---------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------- | ------------------------------------------------------------------------------- |
| `/api/visits`                       | **POST**   | Create a new patient visit. Registers a patient into the system with department, service, and shift. | `VisitRequest` (patientCccd, departmentId, serviceId, shift, reason, priorityLevel) | `VisitResponse` (new visit with ID, status = WAITING, queue number, timestamps) |
| `/api/visits`                       | **GET**    | List all visits. Can filter by status, department, and date.                                         | Query params: `status`, `departmentId`, `date`                                      | List of `VisitResponse`                                                         |
| `/api/visits/{visitId}`             | **GET**    | Get details of a specific visit by ID.                                                               | Path param: `visitId`                                                               | `VisitResponse` (full visit details)                                            |
| `/api/visits/{visitId}/assign`      | **PUT**    | Assign a doctor and room to a visit. Changes status → ASSIGNED.                                      | Path param: `visitId` <br> Query params: `doctorId`, `roomId`                       | Updated `VisitResponse`                                                         |
| `/api/visits/{visitId}/status`      | **PUT**    | Update status of a visit (WAITING → EXAMINING → DONE). Uses optimistic locking.                      | Path param: `visitId` <br> Query params: `newStatus`, `lockVersion`                 | Updated `VisitResponse`                                                         |
| `/api/visits/cancel`                | **DELETE** | Cancel a visit before it starts (patient no-show, etc.).                                             | `CancelRequest` (cccd, reason)                                                      | `204 No Content`                                                                |
| `/api/visits/patient/{cccd}`        | **GET**    | Get the latest visit of a patient by CCCD.                                                           | Path param: `cccd`                                                                  | `VisitResponse`                                                                 |
| `/api/visits/{visitId}/status/done` | **PUT**    | Mark a visit as completed. Sets status = DONE.                                                       | Path param: `visitId`                                                               | Updated `VisitResponse`                                                         |


💊 Prescription API Endpoints

| **Endpoint**                                           | **Method** | **Description**                                      | **Input**                                                                                                   | **Output**                                          |
| ------------------------------------------------------ | ---------- | ---------------------------------------------------- | ----------------------------------------------------------------------------------------------------------- | --------------------------------------------------- |
| `/api/visits/{visitId}/prescriptions`                  | **GET**    | Get prescription of a visit by `visitId`.            | Path param: `visitId`                                                                                       | `PrescriptionResponse`                              |
| `/api/visits/{visitId}/prescriptions`                  | **POST**   | Create a new prescription for a visit.               | Path param: `visitId` <br> Body: `PrescriptionRequest` (drug list, dosage, instructions, doctor info, etc.) | `PrescriptionResponse` (newly created prescription) |
| `/api/visits/{visitId}/prescriptions/{prescriptionId}` | **PUT**    | Update an existing prescription by `prescriptionId`. | Path param: `visitId`, `prescriptionId` <br> Body: `PrescriptionRequest`                                    | Updated `PrescriptionResponse`                      |


🧑‍⚕️ Patient API Endpoints

| **Endpoint**                | **Method** | **Description**                               | **Input**                                      | **Output**                           |
| --------------------------- | ---------- | --------------------------------------------- | ---------------------------------------------- | ------------------------------------ |
| `/api/patients`             | **POST**   | Create a new patient record.                  | Body: `PatientRequest`                         | `PatientResponse` (new patient info) |
| `/api/patients/{id}`        | **GET**    | Get patient details by **ID**.                | Path param: `id`                               | `PatientResponse`                    |
| `/api/patients/cccd/{cccd}` | **GET**    | Get patient details by **CCCD** (citizen ID). | Path param: `cccd`                             | `PatientResponse`                    |
| `/api/patients`             | **GET**    | Get list of all patients.                     | —                                              | `List<PatientResponse>`              |
| `/api/patients/{id}`        | **PUT**    | Update patient info by **ID**.                | Path param: `id` <br> Body: `PatientRequest`   | Updated `PatientResponse`            |
| `/api/patients/cccd/{cccd}` | **PUT**    | Update patient info by **CCCD**.              | Path param: `cccd` <br> Body: `PatientRequest` | Updated `PatientResponse`            |
| `/api/patients/{id}`        | **DELETE** | Delete patient record by **ID**.              | Path param: `id`                               | No content (`204`)                   |
| `/api/patients/cccd/{cccd}` | **DELETE** | Delete patient record by **CCCD**.            | Path param: `cccd`                             | No content (`204`)                   |


### LabRecord API

| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| **POST** | `/api/visits/{visitId}/lab-results` | Tạo kết quả cận lâm sàng mới cho một visit | `LabRecord` (JSON) | `201 Created` + `LabRecord` |
| **GET** | `/api/visits/{visitId}/lab-results` | Lấy danh sách kết quả cận lâm sàng theo visit | - | `200 OK` + `List<LabRecord>` |
| **PUT** | `/api/visits/{visitId}/lab-results` | Cập nhật kết quả cận lâm sàng mới nhất theo visit | `LabRecord` (JSON) | `200 OK` + `LabRecord` |
| **PUT** | `/api/visits/lab-results/{cccd}?type={type}` | Cập nhật kết quả cận lâm sàng theo CCCD và loại xét nghiệm | `LabRecord` (JSON) | `200 OK` + `LabRecord` |
| **DELETE** | `/api/visits/{visitId}/lab-results/{Id}` | Xóa kết quả cận lâm sàng theo ID trong visit | - | `204 No Content` (nếu thành công) / `404 Not Found` |


Clinical Record Endpoint

| Method | Endpoint                           | Description                                        | Request Body                     | Response Body              |
|--------|-------------------------------------|----------------------------------------------------|----------------------------------|----------------------------|
| POST   | `/api/visits/{visitId}/clinical`    | Tạo bệnh án lâm sàng cho một Visit                 | `ClinicalRecord`                 | `ClinicalRecord` (created) |
| GET    | `/api/visits/{visitId}/clinical`    | Lấy bệnh án lâm sàng theo Visit ID                 | -                                | `ClinicalRecord`           |
| PUT    | `/api/visits/{visitId}/clinical`    | Cập nhật bệnh án lâm sàng theo Visit ID            | `ClinicalRecord`                 | `ClinicalRecord` (updated) |
| PUT    | `/api/visits/clinical/{patientCccd}`| Cập nhật bệnh án lâm sàng theo CCCD bệnh nhân      | `ClinicalRecord`                 | `ClinicalRecord` (updated) |

Authen endpoint 
| Method | Endpoint           | Description                     | Request Body    | Response Body   |
|--------|--------------------|---------------------------------|-----------------|-----------------|
| POST   | `/api/auth/login`  | Đăng nhập, trả về thông tin JWT | `LoginRequest`  | `LoginResponse` |
