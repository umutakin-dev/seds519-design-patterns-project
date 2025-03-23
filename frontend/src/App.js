import React, { useState } from "react";

function App() {
    const [htmlContent, setHtmlContent] = useState("");

    // Example HTML content for simulation (you can customize this as needed)
    const excelExampleHtml = `
    <table border="1" style="border-collapse: collapse; width: 100%;">
      <tr>
        <th>Time</th>
        <th>Monday</th>
        <th>Tuesday</th>
        <th>Wednesday</th>
        <th>Thursday</th>
        <th>Friday</th>
      </tr>
      <tr>
        <td>08:45-9:30</td>
        <td>D1, ENG102</td>
        <td>SA</td>
        <td></td>
        <td></td>
        <td></td>
      </tr>
      <tr>
        <td>09:45-10:30</td>
        <td>D0</td>
        <td>CENG214</td>
        <td>BGA</td>
        <td>D0</td>
        <td>CENG112</td>
      </tr>
    </table>
  `;

    const pdfExampleHtml = `
    <div>
      <h3>PDF Converted Schedule</h3>
      <table border="1" style="border-collapse: collapse; width: 100%;">
        <tr>
          <th>Course</th>
          <th>Time</th>
          <th>Room</th>
        </tr>
        <tr>
          <td>ENG102</td>
          <td>08:45-9:30</td>
          <td>SA</td>
        </tr>
        <tr>
          <td>CENG214</td>
          <td>09:45-10:30</td>
          <td>BGA</td>
        </tr>
      </table>
    </div>
  `;

    // Simulate converting Excel by setting example HTML content
    const handleConvertExcel = () => {
        setHtmlContent(excelExampleHtml);
    };

    // Simulate converting PDF by setting example HTML content
    const handleConvertPdf = () => {
        setHtmlContent(pdfExampleHtml);
    };

    return (
        <div style={{ padding: "20px" }}>
            <h2>PDF/Excel to HTML Converter Simulation</h2>
            <div style={{ marginBottom: "20px" }}>
                <button
                    onClick={handleConvertExcel}
                    style={{ marginRight: "10px" }}
                >
                    Convert Excel
                </button>
                <button onClick={handleConvertPdf}>Convert PDF</button>
            </div>

            <div style={{ border: "1px solid #ccc", padding: "10px" }}>
                <h4>Converted HTML:</h4>
                {htmlContent ? (
                    <div dangerouslySetInnerHTML={{ __html: htmlContent }} />
                ) : (
                    <p>
                        No content yet. Click a button above to simulate
                        conversion.
                    </p>
                )}
            </div>
        </div>
    );
}

export default App;
