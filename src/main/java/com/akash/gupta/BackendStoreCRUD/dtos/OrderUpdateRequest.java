package com.akash.gupta.BackendStoreCRUD.dtos;

import lombok.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderUpdateRequest {

    private String orderStatus;
    private String paymentStatus;

    private String billingName;

    private String billingPhone;

    private String billingAddress;

    private Date deliveredDate;

    public void setDeliveredDateFromString(String deliveredDateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            this.deliveredDate = sdf.parse(deliveredDateString);
        } catch (ParseException e) {
            // Handle parse exception
            e.printStackTrace();
        }


    }
}

//  For any Query Contact : akashguptaworks@gmail.com or skyrisegupta@gmail.com
