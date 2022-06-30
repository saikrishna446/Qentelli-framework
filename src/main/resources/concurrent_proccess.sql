SELECT   cr.request_id,
            DECODE (cp.user_concurrent_program_name,
                    'Report Set', 'Report Set:' || cr.description,
                    cp.user_concurrent_program_name)
               NAME,
            cr.description,
            argument_text,
            cr.resubmit_interval,
            NVL2 (cr.resubmit_interval,
                  'PERIODICALLY',
                  NVL2 (cr.release_class_id, 'ON SPECIFIC DAYS', 'ONCE'))
               schedule_type,
            DECODE (
               NVL2 (cr.resubmit_interval,
                     'PERIODICALLY',
                     NVL2 (cr.release_class_id, 'ON SPECIFIC DAYS', 'ONCE')),
               'PERIODICALLY',
                  'EVERY '
               || cr.resubmit_interval
               || ' '
               || cr.resubmit_interval_unit_code
               || ' FROM '
               || cr.resubmit_interval_type_code
               || ' OF PREV RUN',
               'ONCE',
               'AT :'
               || TO_CHAR (cr.requested_start_date, 'DD-MON-RR HH24:MI'),
               'EVERY: ' || fcr.class_info
            )
               schedule,
            fu.user_name,
            TO_CHAR (cr.requested_start_date, 'DD-MON-RR HH24:MI:SS')
               requested_start_date
     FROM   apps.fnd_concurrent_programs_tl cp,
            apps.fnd_concurrent_requests cr,
            apps.fnd_user fu,
            apps.fnd_conc_release_classes fcr
    WHERE       cp.application_id = cr.program_application_id
            AND cp.concurrent_program_id = cr.concurrent_program_id 
            AND cr.requested_by = fu.user_id
            AND cr.phase_code = 'P'
            AND cr.requested_start_date > SYSDATE
            AND cp.LANGUAGE = 'US'
            AND fcr.release_class_id(+) = cr.release_class_id
            AND fcr.application_id(+) = cr.release_class_app_id