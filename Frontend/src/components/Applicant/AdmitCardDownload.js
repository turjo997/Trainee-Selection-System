import React from 'react';

const AdmitCardDownload = () => {
  const handleDownloadAdmitCard = () => {
    const userId = localStorage.getItem('userId');
    if (userId) {
      window.open(`http://localhost:8082/download/${userId}`, '_blank');
    } else {
      console.error('User ID not found in local storage.');
    }
  };

  return (
    <div>
      <h2>Download Admit Card</h2>
      <button onClick={handleDownloadAdmitCard}>Download Admit Card</button>
    </div>
  );
};

export default AdmitCardDownload;
