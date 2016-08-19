import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClaimDao {
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;

    public ClaimDao() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection("jdbc:postgresql://127.0.0.1:5432/mis_development", "challendy", "");
            System.out.println("Opened database successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void insertOrUpdate(Claim claim) {
        if(this.exists(claim.getClaimNumber())){
            this.update(claim);
        } else {
            this.insert(claim);
        }
    }

    public void insert(Claim claim) {
        String insertClaimSQL = "INSERT INTO claims " +
                "(claim_number, provider_id, status_code, location_code ," +
                "bill_type_code, admission_date, received_date, from_date," +
                "to_date, patient_last_name, patient_first_initial, " +
                "charge_total, provider_reimbursement, paid_date, cancel_date," +
                "reason_code, nonpayment_code) " +
                "VALUES " +
                "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            preparedStatement = this.connection.prepareStatement(insertClaimSQL);
            preparedStatement.setString(1, claim.getClaimNumber());
            preparedStatement.setString(2, claim.getProviderId());
            preparedStatement.setString(3, claim.getStatusCode());
            preparedStatement.setString(4, claim.getLocationCode());
            preparedStatement.setString(5, claim.getBillTypeCode());
            preparedStatement.setDate(6, claim.getAdmissionDate());
            preparedStatement.setDate(7, claim.getReceivedDate());
            preparedStatement.setDate(8, claim.getFromDate());
            preparedStatement.setDate(9, claim.getToDate());
            preparedStatement.setString(10, claim.getPatientLastName());
            preparedStatement.setString(11, claim.getPatientFirstInitial());
            preparedStatement.setBigDecimal(12, claim.getChargeTotal());
            preparedStatement.setBigDecimal(13, claim.getProviderReimbursement());
            preparedStatement.setDate(14, claim.getPaidDate());
            preparedStatement.setDate(15, claim.getCancelDate());
            preparedStatement.setString(16, claim.getReasonCode());
            preparedStatement.setString(17, claim.getNonpaymentCode());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Claim claim) {
        String updateClaimSQL = "UPDATE claims SET provider_id = ?, " +
                "status_code = ?, location_code  = ?, bill_type_code = ?, admission_date = ?, " +
                "received_date = ?, from_date = ?, to_date = ?, patient_last_name = ?, " +
                "patient_first_initial = ?, charge_total = ?, provider_reimbursement = ?, " +
                "paid_date = ?, cancel_date = ?,reason_code = ?, nonpayment_code = ? " +
                "WHERE claim_number = ?";
        try {
            preparedStatement = this.connection.prepareStatement(updateClaimSQL);
            preparedStatement.setString(1, claim.getProviderId());
            preparedStatement.setString(2, claim.getStatusCode());
            preparedStatement.setString(3, claim.getLocationCode());
            preparedStatement.setString(4, claim.getBillTypeCode());
            preparedStatement.setDate(5, claim.getAdmissionDate());
            preparedStatement.setDate(6, claim.getReceivedDate());
            preparedStatement.setDate(7, claim.getFromDate());
            preparedStatement.setDate(8, claim.getToDate());
            preparedStatement.setString(9, claim.getPatientLastName());
            preparedStatement.setString(10, claim.getPatientFirstInitial());
            preparedStatement.setBigDecimal(11, claim.getChargeTotal());
            preparedStatement.setBigDecimal(12, claim.getProviderReimbursement());
            preparedStatement.setDate(13, claim.getPaidDate());
            preparedStatement.setDate(14, claim.getCancelDate());
            preparedStatement.setString(15, claim.getReasonCode());
            preparedStatement.setString(16, claim.getNonpaymentCode());
            preparedStatement.setString(17, claim.getClaimNumber());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean exists (String claimNumber) {
        boolean exists = false;
        String selectSQL = "SELECT 1 FROM CLAIMS WHERE claim_number = ?";
        try {
            preparedStatement = this.connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, claimNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                exists = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

    public void close(){
        try {
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
