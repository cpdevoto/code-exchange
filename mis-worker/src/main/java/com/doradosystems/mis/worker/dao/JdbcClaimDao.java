package com.doradosystems.mis.worker.dao;

import static java.util.Objects.requireNonNull;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.devoware.homonculus.database.util.DataAccessException;

import com.doradosystems.mis.agent.model.Claim;

public class JdbcClaimDao implements ClaimDao {

    private final DataSource dataSource;

    @Inject
    public JdbcClaimDao(@Nonnull DataSource dataSource) {
        this.dataSource = requireNonNull(dataSource);
    }


    @Override
    public void insertOrUpdate(@Nonnull Claim claim) {
        try (Connection connection = dataSource.getConnection()) {
            if (exists(connection, claim.getClaimNumber())) {
                update(connection, claim);
            } else {
                insert(connection, claim);
            }
        } catch (SQLException e) {
            throw new DataAccessException("A problem occurred while attempting to establish a connection to the database", e);
        }
    }


    private boolean exists(Connection connection, String claimNumber) {
        boolean exists = false;
        String selectSQL = "SELECT 1 FROM CLAIMS WHERE claim_number = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, claimNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    exists = true;
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("A problem occurred while attempting to check whether a claim exists", e);
        }
        return exists;
    }


    private void update(Connection connection, Claim claim) {
        String updateClaimSQL = "UPDATE claims SET provider_id = ?, " +
                "status_code = ?, location_code  = ?, bill_type_code = ?, admission_date = ?, " +
                "received_date = ?, from_date = ?, to_date = ?, patient_last_name = ?, " +
                "patient_first_initial = ?, charge_total = ?, provider_reimbursement = ?, " +
                "paid_date = ?, cancel_date = ?,reason_code = ?, nonpayment_code = ? " +
                "WHERE claim_number = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateClaimSQL);
            preparedStatement.setString(1, claim.getProviderId());
            preparedStatement.setString(2, claim.getStatusCode());
            preparedStatement.setString(3, claim.getLocationCode());
            preparedStatement.setString(4, claim.getBillTypeCode());
            preparedStatement.setDate(5, new Date(claim.getAdmissionDate().getTime()));
            preparedStatement.setDate(6, new Date(claim.getReceivedDate().getTime()));
            preparedStatement.setDate(7, new Date(claim.getFromDate().getTime()));
            preparedStatement.setDate(8, new Date(claim.getToDate().getTime()));
            preparedStatement.setString(9, claim.getPatientLastName());
            preparedStatement.setString(10, claim.getPatientFirstInitial());
            preparedStatement.setBigDecimal(11, claim.getChargeTotal());
            preparedStatement.setBigDecimal(12, claim.getProviderReimbursement());
            preparedStatement.setDate(13, new Date(claim.getPaidDate().getTime()));
            preparedStatement.setDate(14, new Date(claim.getCancelDate().getTime()));
            preparedStatement.setString(15, claim.getReasonCode());
            preparedStatement.setString(16, claim.getNonpaymentCode());
            preparedStatement.setString(17, claim.getClaimNumber());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new DataAccessException("A problem occurred while attempting to update an exisiting claim ", e);
        }
    }


    private void insert(Connection connection, Claim claim) {
        String insertClaimSQL = "INSERT INTO claims " +
                "(claim_number, provider_id, status_code, location_code ," +
                "bill_type_code, admission_date, received_date, from_date," +
                "to_date, patient_last_name, patient_first_initial, " +
                "charge_total, provider_reimbursement, paid_date, cancel_date," +
                "reason_code, nonpayment_code) " +
                "VALUES " +
                "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertClaimSQL);
            preparedStatement.setString(1, claim.getClaimNumber());
            preparedStatement.setString(2, claim.getProviderId());
            preparedStatement.setString(3, claim.getStatusCode());
            preparedStatement.setString(4, claim.getLocationCode());
            preparedStatement.setString(5, claim.getBillTypeCode());
            preparedStatement.setDate(6, new Date(claim.getAdmissionDate().getTime()));
            preparedStatement.setDate(7, new Date(claim.getReceivedDate().getTime()));
            preparedStatement.setDate(8, new Date(claim.getFromDate().getTime()));
            preparedStatement.setDate(9, new Date(claim.getToDate().getTime()));
            preparedStatement.setString(10, claim.getPatientLastName());
            preparedStatement.setString(11, claim.getPatientFirstInitial());
            preparedStatement.setBigDecimal(12, claim.getChargeTotal());
            preparedStatement.setBigDecimal(13, claim.getProviderReimbursement());
            preparedStatement.setDate(14, new Date(claim.getPaidDate().getTime()));
            preparedStatement.setDate(15, new Date(claim.getCancelDate().getTime()));
            preparedStatement.setString(16, claim.getReasonCode());
            preparedStatement.setString(17, claim.getNonpaymentCode());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new DataAccessException("A problem occurred while attempting to insert a claim ", e);
        }
    }

}
