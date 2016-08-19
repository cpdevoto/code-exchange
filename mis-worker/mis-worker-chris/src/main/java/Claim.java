import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.sql.Date;

public class Claim {
    private long id;
    @JsonProperty("claim_number")
    private String claimNumber;

    @JsonProperty("provider_id")
    private String providerId;

    @JsonProperty("status_code")
    private String statusCode;

    @JsonProperty("location_code")
    private String locationCode;

    @JsonProperty("bill_type_code")
    private String billTypeCode;

    @JsonProperty("admission_date")
    private Date admissionDate;

    @JsonProperty("received_date")
    private Date receivedDate;

    @JsonProperty("from_date")
    private Date fromDate;

    @JsonProperty("to_date")
    private Date toDate;

    @JsonProperty("patient_last_name")
    private String patientLastName;

    @JsonProperty("patient_first_initial")
    private String patientFirstInitial;

    @JsonProperty("charge_total")
    private BigDecimal chargeTotal;

    @JsonProperty("provider_reimbursement")
    private BigDecimal providerReimbursement;

    @JsonProperty("paid_date")
    private Date paidDate;

    @JsonProperty("cancel_date")
    private Date cancelDate;

    @JsonProperty("reason_code")
    private String reasonCode;

    @JsonProperty("nonpayment_code")
    private String nonpaymentCode;

    public Claim() {}

    public String getClaimNumber() {
        return claimNumber;
    }

    public String getProviderId() {
        return providerId;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public String getBillTypeCode() {
        return billTypeCode;
    }

    public Date getAdmissionDate() {
        return admissionDate;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public String getPatientFirstInitial() {
        return patientFirstInitial;
    }

    public BigDecimal getChargeTotal() {
        return chargeTotal;
    }

    public BigDecimal getProviderReimbursement() {
        return providerReimbursement;
    }

    public Date getPaidDate() {
        return paidDate;
    }

    public Date getCancelDate() {
        return cancelDate;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public String getNonpaymentCode() {
        return nonpaymentCode;
    }

    @Override
    public String toString() {
        return "Claim{" +
                "id=" + id +
                ", claimNumber='" + claimNumber + '\'' +
                ", providerId='" + providerId + '\'' +
                ", statusCode='" + statusCode + '\'' +
                ", locationCode='" + locationCode + '\'' +
                ", billTypeCode='" + billTypeCode + '\'' +
                ", admissionDate=" + admissionDate +
                ", receivedDate=" + receivedDate +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", patientLastName='" + patientLastName + '\'' +
                ", patientFirstInitial='" + patientFirstInitial + '\'' +
                ", chargeTotal=" + chargeTotal +
                ", providerReimbursement=" + providerReimbursement +
                ", paidDate=" + paidDate +
                ", cancelDate=" + cancelDate +
                ", reasonCode='" + reasonCode + '\'' +
                ", nonpaymentCode='" + nonpaymentCode + '\'' +
                '}';
    }
}
